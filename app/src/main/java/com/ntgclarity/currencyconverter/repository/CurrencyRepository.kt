package com.ntgclarity.currencyconverter.repository

import com.ntgclarity.currencyconverter.network.AllCurrenciesList
import javax.inject.Inject


class CurrencyRepository  @Inject constructor(private val api: AllCurrenciesList) {
     suspend fun getCurrencyCodes(): Map<String, Double> {
        val response = api.getCurrenciesList("3f384ae1930cb8af787e779c62a2d859")
        return response.rates
    }

}