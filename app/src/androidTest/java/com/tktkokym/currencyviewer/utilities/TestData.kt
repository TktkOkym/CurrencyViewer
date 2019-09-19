package com.tktkokym.currencyviewer.utilities

import com.tktkokym.currencyviewer.data.AmountCurrencyData
import com.tktkokym.currencyviewer.room.CurrencyBaseTable
import java.util.*

const val BASE_CURRENCY = "USD"
val currentTimeStamp get() = Date().time /1000

val currencyBaseData = CurrencyBaseTable(BASE_CURRENCY, currentTimeStamp,
    mapOf("USD" to 1.33, "JPY" to 10.55, "KRW" to 100.44))

val expectedAmountCurrency = AmountCurrencyData(100.0, BASE_CURRENCY)