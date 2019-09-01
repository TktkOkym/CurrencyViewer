package com.tktkokym.currencyviewer.data

data class SelectedCurrencyList(
    var base: String,
    var timestamp: Long,
    var currencyRateList: List<CurrencyItem>
)