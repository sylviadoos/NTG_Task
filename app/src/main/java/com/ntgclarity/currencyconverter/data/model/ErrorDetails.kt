package com.ntgclarity.currencyconverter.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorDetails(
    val code: Int,
    val type: String,
    val info: String
)