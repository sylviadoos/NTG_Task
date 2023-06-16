package com.ntgclarity.currencyconverter.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ntgclarity.currencyconverter.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.database.asDomainModel
import com.ntgclarity.currencyconverter.domain.CurrenciesListItem
import com.ntgclarity.currencyconverter.network.AllCurrenciesList
import javax.inject.Inject


class CurrencyRepository  @Inject constructor(private val api: AllCurrenciesList
) {


     suspend fun getCurrencyCodes(accessKey:String): Map<String, Double> {
        val response = api.getCurrenciesList(accessKey)
        return response.rates
    }

}