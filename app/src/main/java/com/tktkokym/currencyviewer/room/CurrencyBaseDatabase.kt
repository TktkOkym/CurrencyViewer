package com.tktkokym.currencyviewer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CurrencyBaseTable::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class CurrencyBaseDatabase : RoomDatabase() {
    abstract fun currencyRatesDao(): CurrencyRatesDao
}