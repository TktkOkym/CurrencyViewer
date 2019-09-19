package com.tktkokym.currencyviewer.util

import com.tktkokym.currencyviewer.data.CurrencyItem
import com.tktkokym.currencyviewer.data.SelectedCurrencyList
import com.tktkokym.currencyviewer.viewmodel.CurrencyViewModel

fun getSelectedCurrencyList(
    currencyData: CurrencyViewModel.AmountCurrencyData,
    baseCurrentList: List<CurrencyItem>,
    timeStamp: Long
): SelectedCurrencyList {
    // get rates of selected currency
    val newRate =
        baseCurrentList.find { item ->
            item.currency.getTargetCurrency() == currencyData.currency }?.rate ?: 1.0

    return SelectedCurrencyList(
        currencyData.currency,
        timeStamp,
        baseCurrentList.map { item ->
            item.copy(
                currency = item.currency,
                rate = item.rate / newRate * currencyData.amount
            )
        })
}