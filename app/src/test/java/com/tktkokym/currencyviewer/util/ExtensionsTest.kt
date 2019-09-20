package com.tktkokym.currencyviewer.util

import com.tktkokym.currencyviewer.data.CurrencyItem
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class ExtensionsTest {
    @Test
    fun testIsOld() {
        val currentTimeStamp = Date().time / 1000
        assertTrue(0L.isOld())
        assertFalse(null.isOld())
        assertFalse(currentTimeStamp.isOld())
    }

    @Test
    fun testDateTimeToUnixTimeStamp() {
        assertEquals(0L, null.dateTimeToUnixTimeStamp())
        assertEquals(1L, Date().apply { time = 1000 }.dateTimeToUnixTimeStamp())
    }

    @Test
    fun testTargetCurrency() {
        assertEquals("", null.getTargetCurrency())
        assertEquals("", "".getTargetCurrency())
        assertEquals("USD", "JPYUSD".getTargetCurrency())
    }

    @Test
    fun testWrongDoubleFormat() {
        assertTrue(null.isWrongDoubleFormat())
        assertTrue("".isWrongDoubleFormat())
        assertTrue(".".isWrongDoubleFormat())
        assertTrue("00".isWrongDoubleFormat())
        assertTrue("1.0.".isWrongDoubleFormat())
        assertFalse("1.0".isWrongDoubleFormat())
    }

    @Test
    fun testCurrencyMapToList() {
        val expected = listOf(
            CurrencyItem("USDUSD", 1.0),
            CurrencyItem("USDJPY", 100.0),
            CurrencyItem("USDKRW", 1000.0)
        )

        assertEquals(
            expected,
            mapOf("USDUSD" to 1.0, "USDJPY" to 100.0, "USDKRW" to 1000.0).convertToCurrencyList())
    }

    @Test
    fun testFormatDateString() {
        val unixTimeStamp = 1568854250L
        val expected = "last updated: 2019-09-19 09:50"
        assertEquals(expected, unixTimeStamp.formatDateString())
    }
}