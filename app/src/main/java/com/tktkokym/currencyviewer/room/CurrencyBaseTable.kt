package com.tktkokym.currencyviewer.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_table")
data class CurrencyBaseTable(
    @PrimaryKey
    val base: String,
    val timestamp: Long,
    val rates: Map<String, Double>
)