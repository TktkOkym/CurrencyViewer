package com.tktkokym.currencyviewer.util

import com.tktkokym.currencyviewer.constants.Constants
import com.tktkokym.currencyviewer.data.CurrencyItem
import java.text.SimpleDateFormat
import java.util.*


// check
fun Long?.isOld(): Boolean {
    // 30 mins ago from current time to timestamp format
    val thirtyMinsAgo = (Date().time - Constants.THIRTY_MINUTES) / 1000
    this?.let { return it < thirtyMinsAgo }
    return false
}

fun Date?.dateTimeToUnixTimeStamp(): Long {
    val dateTime = this?.time
    return if (dateTime != null) dateTime / 1000 else 0L
}

fun String?.getTargetCurrency(): String {
    return if (this.isNullOrBlank()) "" else this.removeRange(0..2)
}

fun String?.isWrongDoubleFormat(): Boolean {
    return this.isNullOrBlank() || this == "00" || this == "." || this.count { it == '.' } > 1
}

fun Map<String, Double>.convertToCurrencyList(): List<CurrencyItem> {
    return this.map { CurrencyItem(it.key, it.value) }
}

fun Long.formatDateString(): String {
    return "last updated: " + SimpleDateFormat(
        Constants.DATE_FORMAT,
        Locale.getDefault()).format(Date().apply { time = this@formatDateString * 1000 })
}