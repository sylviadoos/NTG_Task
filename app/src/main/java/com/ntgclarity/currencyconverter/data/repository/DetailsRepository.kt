package com.ntgclarity.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ntgclarity.currencyconverter.data.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.data.database.asDomainModel
import com.ntgclarity.currencyconverter.data.database.domain.CurrenciesListItem
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