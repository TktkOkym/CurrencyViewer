package com.tktkokym.currencyviewer.repository

import android.content.Context
import com.tktkokym.currencyviewer.constants.NetworkConstants
import com.tktkokym.currencyviewer.network.CurrencyApi
import com.tktkokym.currencyviewer.room.CurrencyRatesDao
import com.tktkokym.currencyviewer.room.CurrencyBaseTable
import com.tktkokym.currencyviewer.util.isInternetConnected
import com.tktkokym.currencyviewer.util.isOld
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class CurrencyRepository: KoinComponent {
    private val networkService: CurrencyApi by inject()
    private val currencyRatesDao: CurrencyRatesDao by inject()
    private val application: Context by inject()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    // get Currency Data from Server
    fun getCurrencyNetworkResponse() {
        scope.launch {
            if (isInternetConnected(application)) {
                val response = networkService.getCurrencyData(NetworkConstants.ACCESS_KEY)
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.success && it.timestamp.isOld()) {
                            insertNewCurrencyToDB(CurrencyBaseTable(it.source, it.timestamp, it.quotes))
                        }
                    }
                }
            }
        }
    }

    // get Currency Data from Room DB
    fun getBaseCurrencyFromDB() = currencyRatesDao.getBase()

    // insert new network response to DB
    private fun insertNewCurrencyToDB(newCurrencyBase: CurrencyBaseTable) = currencyRatesDao.insert(newCurrencyBase)
}