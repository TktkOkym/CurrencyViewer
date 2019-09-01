package com.tktkokym.currencyviewer.network

import androidx.annotation.NonNull
import com.tktkokym.currencyviewer.constants.NetworkConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilderFactory {
    @NonNull
    private fun getApiServices(): CurrencyApi {
        val builder = OkHttpClient().newBuilder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        builder.addInterceptor(httpLoggingInterceptor)
        val client = builder.build()

        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CurrencyApi::class.java)
    }

    companion object {
        fun getApiServices(): CurrencyApi {
            return RetrofitBuilderFactory().getApiServices()
        }
    }
}