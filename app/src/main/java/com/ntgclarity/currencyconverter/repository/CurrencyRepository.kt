package com.ntgclarity.currencyconverter.repository

import com.ntgclarity.currencyconverter.network.AllCurrenciesList
import javax.inject.Inject


class CurrencyRepository  @Inject constructor(private val api: AllCurrenciesList) {
     suspend fun getCurrencyCodes(accessKey:String): Map<String, Double> {
        val response = api.getCurrenciesList(accessKey)
        return response.rates
    }

}