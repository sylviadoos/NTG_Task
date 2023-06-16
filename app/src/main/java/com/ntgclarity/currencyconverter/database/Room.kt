package com.ntgclarity.currencyconverter.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ntgclarity.currencyconverter.domain.CurrenciesListItem

@Dao
interface CurrenciesDao {

    // user List
    @Query("select * from DatabaseCurrenciesListItem ")
    fun getDatabaseCurrencies(): LiveData<List<DatabaseCurrenciesListItem>>

    @Insert
    fun insertAll(currenciesListItem: DatabaseCurrenciesListItem)


 }

@Database(entities = [DatabaseCurrenciesListItem::class], version = 1, exportSchema = false)
abstract class CurrenciesDatabase : RoomDatabase() {
    abstract val currenciesDao: CurrenciesDao
}