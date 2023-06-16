package com.ntgclarity.currencyconverter.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ntgclarity.currencyconverter.domain.CurrenciesListItem

@Entity
data class DatabaseCurrenciesListItem constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dateConvert: String ,
    val currencyFrom: String,
    val currencyTo: String
)

fun DatabaseCurrenciesListItem.asDomainModel(): CurrenciesListItem {
    return CurrenciesListItem(
            dateConvert = dateConvert,
            currencyFrom = currencyFrom,
            currencyTo = currencyTo
        )

}


fun List<DatabaseCurrenciesListItem>.asDomainModel(): List<CurrenciesListItem> {

    return map {
        CurrenciesListItem(
            dateConvert = it.dateConvert,
            currencyFrom =  it.currencyFrom,
            currencyTo =  it.currencyTo
        )
    }
}
