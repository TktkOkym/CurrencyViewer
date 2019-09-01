package com.tktkokym.currencyviewer.viewmodel

import androidx.lifecycle.*
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.tktkokym.currencyviewer.constants.Constants
import com.tktkokym.currencyviewer.data.SelectedCurrencyList
import com.tktkokym.currencyviewer.network.Status
import com.tktkokym.currencyviewer.repository.CurrencyRepository
import com.tktkokym.currencyviewer.util.DoubleTrigger
import com.tktkokym.currencyviewer.util.convertToCurrencyList
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class CurrencyViewModel: ViewModel(), KoinComponent {
    private val repository: CurrencyRepository by inject()
    private val workRequest: PeriodicWorkRequest by inject()
    private val workManager: WorkManager = WorkManager.getInstance()
    private val _selectedCurrencyList = MutableLiveData<SelectedCurrencyList>()
    private val _amountCurrency = MutableLiveData<AmountCurrencyData>()
    private val _currencyNameList = MutableLiveData<List<String>>()
    private val _status = MutableLiveData<Status>()

    val status: LiveData<Status> get() = _status

    init {
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
        _currencyNameList.postValue(it?.rates?.map { rateItem -> rateItem.key.removeRange(0..2) })
        return@switchMap _currencyNameList
    }

    val currencyUIData: LiveData<SelectedCurrencyList>
            = Transformations.switchMap(DoubleTrigger(_amountCurrency, repository.getBaseCurrencyFromDB())) {
        val currencyData = it.first
        val baseCurrentList = it.second?.rates?.convertToCurrencyList() ?: emptyList()

        if (currencyData != null && baseCurrentList.isNotEmpty()) {
            // get rates of selected currency
            val newRate =
                baseCurrentList.find { item -> item.currency.removeRange(0..2) == currencyData.currency }?.rate ?: 1.0

            _selectedCurrencyList.postValue(
                SelectedCurrencyList(
                    currencyData.currency,
                    it.second?.timestamp ?: Date().time / 1000,
                    baseCurrentList.map { item ->
                        item.copy(currency = item.currency, rate = item.rate / newRate * (currencyData.amount)) })
            )

            _status.postValue(Status.SUCCESS)
        }
        return@switchMap _selectedCurrencyList
    }

    fun setAmountCurrency(amount: Double?, currency: String) {
        _amountCurrency.postValue(AmountCurrencyData(amount ?: 1.0, currency))
    }

    data class AmountCurrencyData(val amount: Double, val currency: String)

    override fun onCleared() {
        super.onCleared()
        workManager.cancelWorkById(workRequest.id)
    }
}