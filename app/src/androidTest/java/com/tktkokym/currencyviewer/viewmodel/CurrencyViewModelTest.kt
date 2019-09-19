package com.tktkokym.currencyviewer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.filters.LargeTest
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import com.tktkokym.currencyviewer.data.SelectedCurrencyList
import com.tktkokym.currencyviewer.network.Status
import com.tktkokym.currencyviewer.repository.CurrencyRepository
import com.tktkokym.currencyviewer.utilities.BASE_CURRENCY
import com.tktkokym.currencyviewer.utilities.expectedAmountCurrency
import com.tktkokym.currencyviewer.utilities.getValue
import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test

@LargeTest
class CurrencyViewModelTest: AutoCloseKoinTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel by inject<CurrencyViewModel>()
    private val repository by inject<CurrencyRepository>()
    private val currencyNameListMock: Observer<List<String>> = mock()
    private val statusMock: Observer<Status> = mock()
    private val currencyUIDataMock: Observer<SelectedCurrencyList> = mock()

    @Before
    fun before() {
        viewModel.status.observeForever(statusMock)
        viewModel.currencyNameList.observeForever(currencyNameListMock)
        viewModel.currencyUIData.observeForever(currencyUIDataMock)
    }

    @Test
    fun testLiveDataValues() {
        //test status
        assertEquals(getValue(viewModel.status), Status.LOADING)
        viewModel.setStatus(Status.SUCCESS)
        assertEquals(Status.SUCCESS, getValue(viewModel.status))

        //test amount currency
        viewModel.setAmountCurrency(100.0, BASE_CURRENCY)
        assertEquals(expectedAmountCurrency, getValue(viewModel.amountCurrency))

        if (!getValue(repository.getBaseCurrencyFromDB()).rates.isNullOrEmpty()) {
            assertNotNull(getValue(viewModel.currencyUIData))
            assertNotNull(getValue(viewModel.currencyNameList))
        } else {
            assertNull(getValue(viewModel.currencyUIData))
            assertNull(getValue(viewModel.currencyNameList))
        }
    }
}