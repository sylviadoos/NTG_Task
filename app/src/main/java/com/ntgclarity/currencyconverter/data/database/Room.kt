package com.ntgclarity.currencyconverter.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ntgclarity.currencyconverter.data.database.domain.CurrenciesListItem

@Dao
interface CurrenciesDao {

    // user List
    @Query("select * from DatabaseCurrenciesListItem where dateConvert between :fromDate and :toDate")
    fun getDatabaseCurrencies(fromDate :String,toDate:String): LiveData<List<DatabaseCurrenciesListItem>>

    @Insert
    fun insertAll(currenciesListItem: DatabaseCurrenciesListItem)


 }

@Database(entities = [DatabaseCurrenciesListItem::class], version = 1, exportSchema = false)
abstract class CurrenciesDatabase : RoomDatabase() {
    abstract val currenciesDao: CurrenciesDao
}