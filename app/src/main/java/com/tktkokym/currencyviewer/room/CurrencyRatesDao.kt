package com.tktkokym.currencyviewer.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy

@Dao
interface CurrencyRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyBaseTable: CurrencyBaseTable)

    @Query("SELECT * FROM currency_table")
    fun getBase(): LiveData<CurrencyBaseTable>

    @Query("SELECT * FROM currency_table WHERE base = :base")
    fun findByBase(base: String): CurrencyBaseTable
}