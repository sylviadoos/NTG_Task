package com.ntgclarity.currencyconverter.data.network

import com.ntgclarity.currencyconverter.data.model.NetworkCurrencyListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApis {

    @GET("/latest")
    suspend fun getCurrenciesList(@Query("access_key") access_key: String): Response<NetworkCurrencyListItem>
}