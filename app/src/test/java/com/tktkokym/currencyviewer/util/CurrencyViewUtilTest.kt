package com.tktkokym.currencyviewer.util

import com.tktkokym.currencyviewer.data.AmountCurrencyData
import com.tktkokym.currencyviewer.data.CurrencyItem
import com.tktkokym.currencyviewer.data.SelectedCurrencyList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CurrencyViewUtilTest {
    private lateinit var expectedUSD: SelectedCurrencyList
    private lateinit var expectedJPY: SelectedCurrencyList
    private val timeStamp = 19827394L

    @Before
    fun setup() {
        expectedUSD = SelectedCurrencyList(
            "USD",
            timeStamp,
            listOf(
                CurrencyItem("USDUSD", 100.0),
                CurrencyItem("USDJPY", 10000.0),
                CurrencyItem("USDKRW", 100000.0))
        )

        expectedJPY = SelectedCurrencyList(
            "JPY",
            timeStamp,
            listOf(
                CurrencyItem("USDUSD", 0.1),
                CurrencyItem("USDJPY", 10.0),
                CurrencyItem("USDKRW", 100.0))
        )
    }

    @Test
    fun testSelectedCurrencyList() {
        assertEquals(expectedUSD,
            getSelectedCurrencyList(
                AmountCurrencyData(100.0, "USD"),
                listOf(
                    CurrencyItem("USDUSD", 1.0),
                    CurrencyItem("USDJPY", 100.0),
                    CurrencyItem("USDKRW", 1000.0)
                ), timeStamp))


        assertEquals(expectedJPY,
            getSelectedCurrencyList(
                AmountCurrencyData(10.0, "JPY"),
                listOf(
                    CurrencyItem("USDUSD", 1.0),
                    CurrencyItem("USDJPY", 100.0),
                    CurrencyItem("USDKRW", 1000.0)
                ), timeStamp))
    }
}