package com.ntgclarity.currencyconverter.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class DatabaseCurrenciesListItem constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val dateConvert: String,
    val currencyFrom: String,
    val currencyTo: String
)

