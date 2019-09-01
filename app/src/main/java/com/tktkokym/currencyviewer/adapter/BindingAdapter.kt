package com.tktkokym.currencyviewer.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat

@BindingAdapter("numberWithComma")
fun setNumberWithComma(textView :TextView, number: Double) {
    textView.text = NumberFormat.getNumberInstance().format(number).toString()
}

@BindingAdapter("formatCurrency")
fun formatCurrency(textView :TextView, currency: String) {
    textView.text = currency.removeRange(0..2)
}