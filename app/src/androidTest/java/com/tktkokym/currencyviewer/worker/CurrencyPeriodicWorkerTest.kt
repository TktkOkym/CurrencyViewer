package com.tktkokym.currencyviewer.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.testing.TestListenableWorkerBuilder
import org.junit.Assert.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class CurrencyPeriodicWorkerTest: AutoCloseKoinTest() {
    private val context: Context by inject()
    private lateinit var workManager: WorkManager

    @Before
    fun setup() {
        workManager = WorkManager.getInstance(context)
    }

    @Test
    fun testRefreshMainDataWork() {
        // Get the ListenableWorker
        val worker = TestListenableWorkerBuilder<CurrencyPeriodicWorker>(context).build()

        // Start the work synchronously
        val result = worker.startWork().get()

        assertThat(result, `is`(ListenableWorker.Result.success()))
    }
}