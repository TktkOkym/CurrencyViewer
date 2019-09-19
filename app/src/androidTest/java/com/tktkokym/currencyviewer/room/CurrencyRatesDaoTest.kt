package com.tktkokym.currencyviewer.room

import androidx.test.filters.MediumTest
import com.tktkokym.currencyviewer.utilities.BASE_CURRENCY
import com.tktkokym.currencyviewer.utilities.currencyBaseData
import com.tktkokym.currencyviewer.utilities.testRoomModule
import org.junit.Assert.assertEquals
import kotlinx.coroutines.*
import org.junit.*
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@MediumTest
class CurrencyRatesDaoTest: KoinTest {
    private val currencyRatesDao by inject<CurrencyRatesDao>()
    private val database by inject<CurrencyBaseDatabase>()

    @Before
    fun before() {
        loadKoinModules(testRoomModule)
    }

    @Test
    fun testInsert() = runBlocking {
        currencyRatesDao.insert(currencyBaseData)
        val dataFromDb = currencyRatesDao.findByBase(BASE_CURRENCY)
        assertEquals(dataFromDb, currencyBaseData)
    }

    @After
    fun after() {
        database.close()
        stopKoin()
    }
}