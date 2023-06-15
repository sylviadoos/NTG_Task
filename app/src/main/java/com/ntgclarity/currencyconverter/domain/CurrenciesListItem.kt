package com.ntgclarity.currencyconverter.domain

import java.sql.Date

data class CurrenciesListItem(
    val dateConvert: Date,
    val currencyFrom: String,
    val currencyTo: String
)
