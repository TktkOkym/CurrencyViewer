package com.tktkokym.currencyviewer.viewmodel

import android.content.Context
import androidx.lifecycle.*
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.tktkokym.currencyviewer.constants.Constants
import com.tktkokym.currencyviewer.data.AmountCurrencyData
import com.tktkokym.currencyviewer.data.SelectedCurrencyList
import com.tktkokym.currencyviewer.network.Status
import com.tktkokym.currencyviewer.repository.CurrencyRepository
import com.tktkokym.currencyviewer.util.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class CurrencyViewModel: ViewModel(), KoinComponent {
    private val repository: CurrencyRepository by inject()
    private val workRequest: PeriodicWorkRequest by inject()
    private val context: Context by inject()
    private var workManager: WorkManager
    private val _selectedCurrencyList = MutableLiveData<SelectedCurrencyList>()
    private val _amountCurrency = MutableLiveData<AmountCurrencyData>()
    private val _currencyNameList = MutableLiveData<List<String>>()
    private val _status = MutableLiveData<Status>()

    val status: LiveData<Status> get() = _status

    init {
        workManager = WorkManager.getInstance(context)
        _status.postValue(Status.LOADING)
        requestPeriodicWorker(isReplace = false)
    }

    fun requestPeriodicWorker(isReplace: Boolean) {
        // USE KEEP to avoid possible cancellation of already running worker
        // REPLACE is used for refresh action only
        val workPolicy =
            if (isReplace) ExistingPeriodicWorkPolicy.REPLACE else ExistingPeriodicWorkPolicy.KEEP
        workManager.enqueueUniquePeriodicWork(Constants.WORKER_TAG, workPolicy, workRequest)
    }

    // currency name list for spinner
    val currencyNameList: LiveData<List<String>> = Transformations.switchMap(repository.getBaseCurrencyFromDB()) {
        _currencyNameList.postValue(it?.rates?.map { rateItem -> rateItem.key.getTargetCurrency() })
        return@switchMap _currencyNameList
    }

    val currencyUIData: LiveData<SelectedCurrencyList>
            = Transformations.switchMap(DoubleTrigger(_amountCurrency, repository.getBaseCurrencyFromDB())) {
        val currencyData = it.first
        val baseCurrentList = it.second?.rates?.convertToCurrencyList() ?: emptyList()
        val timeStamp = it.second?.timestamp ?: Date().dateTimeToUnixTimeStamp()

        if (currencyData != null && baseCurrentList.isNotEmpty()) {
            _selectedCurrencyList.postValue(
                getSelectedCurrencyList(currencyData, baseCurrentList, timeStamp))
            _status.postValue(Status.SUCCESS)
        }
        return@switchMap _selectedCurrencyList
    }

    val amountCurrency: LiveData<AmountCurrencyData> get() = _amountCurrency

    fun setAmountCurrency(amount: Double, currency: String) {
        _amountCurrency.postValue(AmountCurrencyData(amount, currency))
    }

    fun setStatus(status: Status) { _status.postValue(status) }

    override fun onCleared() {
        super.onCleared()
        workManager.cancelWorkById(workRequest.id)
    }
}