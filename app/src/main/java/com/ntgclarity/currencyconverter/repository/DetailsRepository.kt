package com.ntgclarity.currencyconverter.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ntgclarity.currencyconverter.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.database.asDomainModel
import com.ntgclarity.currencyconverter.domain.CurrenciesListItem
import com.ntgclarity.currencyconverter.network.AllCurrenciesList
import javax.inject.Inject


class DetailsRepository  @Inject constructor(private val database: CurrenciesDatabase
) {

    val users: LiveData<List<CurrenciesListItem>> =
        Transformations.map(database.currenciesDao.getDatabaseCurrencies()) {
            it.asDomainModel()
        }


}