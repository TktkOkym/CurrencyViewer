package com.tktkokym.currencyviewer.network

import com.tktkokym.currencyviewer.constants.NetworkConstants
import com.tktkokym.currencyviewer.data.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("live")
    suspend fun getCurrencyData(
        @Query(NetworkConstants.ACCESS_KEY_QUERY) accessKey: String): Response<CurrencyResponse>
}