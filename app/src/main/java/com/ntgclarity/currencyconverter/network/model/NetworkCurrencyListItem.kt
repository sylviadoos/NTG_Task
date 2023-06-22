package com.ntgclarity.currencyconverter.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkCurrencyListItem(
    val success: Boolean,
    val rates: Map<String, Double>

    )




