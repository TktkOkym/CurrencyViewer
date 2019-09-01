package com.tktkokym.currencyviewer.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tktkokym.currencyviewer.data.CurrencyItem

class CurrencyItemDiff : DiffUtil.ItemCallback<CurrencyItem>() {
    override fun areItemsTheSame(
        oldItem: CurrencyItem,
        newItem: CurrencyItem
    ): Boolean {
        return oldItem.currency == newItem.currency // check uniqueness
    }

    override fun areContentsTheSame(
        oldItem: CurrencyItem,
        newItem: CurrencyItem
    ): Boolean {
        return oldItem == newItem  // check contents
    }
}