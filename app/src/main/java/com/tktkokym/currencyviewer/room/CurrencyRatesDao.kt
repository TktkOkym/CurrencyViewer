package com.tktkokym.currencyviewer.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy

@Dao
interface CurrencyRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyBaseTable: CurrencyBaseTable)

    @Query("SELECT * FROM currency_table")
    fun getBase(): LiveData<CurrencyBaseTable>
}