package com.tktkokym.currencyviewer.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tktkokym.currencyviewer.R
import com.tktkokym.currencyviewer.adapter.CurrencyItemListAdapter
import com.tktkokym.currencyviewer.constants.Constants

import com.tktkokym.currencyviewer.databinding.FragmentCurrencyViewerBinding
import com.tktkokym.currencyviewer.network.Status
import com.tktkokym.currencyviewer.util.formatDateString
import com.tktkokym.currencyviewer.util.isWrongDoubleFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.tktkokym.currencyviewer.viewmodel.CurrencyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Currency Viewer screen to display list of currency by input value and selected currency
 */
class CurrencyViewerFragment : Fragment() {
    private lateinit var binding: FragmentCurrencyViewerBinding
    private lateinit var listAdapter: CurrencyItemListAdapter
    private val viewModel: CurrencyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        setUIListener()
        setObserver()
    }

    private fun initUI() {
        listAdapter = CurrencyItemListAdapter()

        binding.currencyListRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = listAdapter
        }
    }

    private fun setUIListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.requestPeriodicWorker(isReplace = true)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.currencySelectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val input = binding.currencyInputEditText.text.toString()
                requestCurrencyUpdate(input, parent?.getItemAtPosition(position).toString())
            }
        }

        binding.currencyInputEditText.addTextChangedListener(object : TextWatcher {
            private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
            private var searchFor = ""

            override fun afterTextChanged(editable: Editable) {
                val input = editable.toString().trim()
                if (searchFor == input) return
                searchFor = input

                if (input.isWrongDoubleFormat()) {
                    val fixedInput = input.dropLast(1)
                    binding.currencyInputEditText.setText(fixedInput)
                    binding.currencyInputEditText.setSelection(fixedInput.length)
                }

                scope.launch {
                    delay(Constants.DEBOUNCE_DELAY) //debounce timeout
                    if (searchFor != input) return@launch
                    requestCurrencyUpdate(input, binding.currencySelectSpinner.selectedItem.toString())
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setObserver() {
        viewModel.currencyUIData.observe(this, Observer {
            if (!it.currencyRateList.isNullOrEmpty()) {
                listAdapter.submitList(it.currencyRateList)
                binding.timeStampText.text = it.timestamp.formatDateString()
            }
        })

        //setup adapter initially or only when currency list response is changed
        var prevList = emptyList<String>()
        viewModel.currencyNameList.observe(this, Observer {
            if (!it.isNullOrEmpty() && prevList != it) {
                prevList = it
                val dataAdapter = ArrayAdapter<String>(activity!!, android.R.layout.simple_spinner_item, it)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.currencySelectSpinner.adapter = dataAdapter
            }
        })

        viewModel.status.observe(this, Observer {
            it?.let { status ->
                when (status) {
                    Status.SUCCESS -> binding.progressBar.visibility = View.GONE
                    Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                    Status.ERROR -> handleError()
                }
            }
        })
    }

    private fun handleError() {
        binding.progressBar.visibility = View.GONE
        Snackbar.make(
            binding.root,
            getString(R.string.network_error),
            Snackbar.LENGTH_LONG
        ).show()
    }

    //update amount of each currency item by new input
    private fun requestCurrencyUpdate(input: String, currency: String) {
        viewModel.setAmountCurrency(if (input.isBlank()) 0.0 else input.toDouble(), currency)
    }
}
