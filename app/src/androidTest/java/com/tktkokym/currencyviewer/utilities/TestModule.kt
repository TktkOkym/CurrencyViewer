package com.tktkokym.currencyviewer.utilities

import androidx.room.Room
import com.tktkokym.currencyviewer.room.CurrencyBaseDatabase
import org.koin.dsl.module

val testRoomModule = module(override = true) {
    single {
        Room.inMemoryDatabaseBuilder(
            get(),
            CurrencyBaseDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    single { get<CurrencyBaseDatabase>().currencyRatesDao() }
}