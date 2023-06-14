package com.ntgclarity.currencyconverter.network

import com.ntgclarity.currencyconverter.network.model.NetworkCurrencyListItem
import dagger.Provides
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AllCurrenciesList {

    @GET("/latest")
    suspend fun getCurrenciesList(@Query("access_key") access_key: String): NetworkCurrencyListItem
}