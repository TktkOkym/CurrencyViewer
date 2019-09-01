package com.tktkokym.currencyviewer.di

import androidx.room.Room
import androidx.work.PeriodicWorkRequestBuilder
import com.tktkokym.currencyviewer.network.RetrofitBuilderFactory
import com.tktkokym.currencyviewer.repository.CurrencyRepository
import com.tktkokym.currencyviewer.room.CurrencyBaseDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.tktkokym.currencyviewer.viewmodel.CurrencyViewModel
import com.tktkokym.currencyviewer.worker.CurrencyPeriodicWorker
import java.util.concurrent.TimeUnit

val networkServiceModule = module {
    single { RetrofitBuilderFactory.getApiServices() }
}

val repositoryModule = module {
    single { CurrencyRepository() }
}

val viewModelModule = module {
    viewModel { CurrencyViewModel() }
}

val roomDatabaseModule = module {
    single { get<CurrencyBaseDatabase>().currencyRatesDao() }
    single { Room.databaseBuilder(get(), CurrencyBaseDatabase::class.java, "currency_base_db").build() }
}

val workerModule = module {
    single { PeriodicWorkRequestBuilder<CurrencyPeriodicWorker>(30L, TimeUnit.MINUTES).build() }
}

val appModule = listOf(
    networkServiceModule,
    repositoryModule,
    viewModelModule,
    roomDatabaseModule,
    workerModule
)