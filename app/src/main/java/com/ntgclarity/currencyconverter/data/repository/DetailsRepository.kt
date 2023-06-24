package com.ntgclarity.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ntgclarity.currencyconverter.data.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.data.database.DatabaseCurrenciesListItem
import com.ntgclarity.currencyconverter.modules.history.models.HistoryUiModel
import java.time.LocalDate
import javax.inject.Inject


class DetailsRepository  @Inject constructor(private val database: CurrenciesDatabase
) {
    val currentDate = LocalDate.now()

    val history: LiveData<List<HistoryUiModel>> =
        Transformations.map(database.currenciesDao.getDatabaseCurrencies(currentDate.minusDays(3).toString(),currentDate.toString())) {
            it
        }


}