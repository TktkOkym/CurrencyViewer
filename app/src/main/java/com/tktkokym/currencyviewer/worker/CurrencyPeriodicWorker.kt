package com.tktkokym.currencyviewer.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.tktkokym.currencyviewer.repository.CurrencyRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class CurrencyPeriodicWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams), KoinComponent {
    private val repository: CurrencyRepository by inject()

    override fun doWork(): Result {
        repository.getCurrencyNetworkResponse()
        Log.d("WORKER: ", "AAA id " + this.id.toString())
        return Result.success()
    }
}