package com.ntgclarity.currencyconverter.domain

import com.ntgclarity.currencyconverter.database.DatabaseCurrenciesListItem
import com.ntgclarity.currencyconverter.network.model.NetworkCurrencyListItem
import java.sql.Date

data class CurrenciesListItem(
    val dateConvert: String,
    val currencyFrom: String,
    val currencyTo: String
)
fun CurrenciesListItem.asDatabaseModel(): DatabaseCurrenciesListItem {
    return DatabaseCurrenciesListItem(
        id=0,
        dateConvert = dateConvert,
        currencyFrom = currencyFrom,
        currencyTo = currencyTo
    )
}
