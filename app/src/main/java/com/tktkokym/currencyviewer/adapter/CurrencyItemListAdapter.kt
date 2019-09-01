package com.tktkokym.currencyviewer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tktkokym.currencyviewer.data.CurrencyItem
import com.tktkokym.currencyviewer.databinding.CurrencyListItemBinding

class CurrencyItemListAdapter : ListAdapter<CurrencyItem,
        CurrencyItemListAdapter.ViewHolder>(CurrencyItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CurrencyListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.also {
            holder.apply {
                bind(it)
                itemView.tag = it
            }
        }
    }

    class ViewHolder(
        private val binding: CurrencyListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CurrencyItem) {
            binding.apply {
                data = item
                executePendingBindings()
            }
        }
    }
}