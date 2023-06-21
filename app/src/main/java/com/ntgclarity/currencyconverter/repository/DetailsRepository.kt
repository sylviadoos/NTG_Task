package com.ntgclarity.currencyconverter.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ntgclarity.currencyconverter.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.database.asDomainModel
import com.ntgclarity.currencyconverter.domain.CurrenciesListItem
import com.ntgclarity.currencyconverter.network.AllCurrenciesList
import java.time.LocalDate
import javax.inject.Inject


class DetailsRepository  @Inject constructor(private val database: CurrenciesDatabase
) {
    val currentDate = LocalDate.now()

    val users: LiveData<List<CurrenciesListItem>> =
        Transformations.map(database.currenciesDao.getDatabaseCurrencies(currentDate.toString(),currentDate.plusDays(3).toString())) {
            it.asDomainModel()
        }


}