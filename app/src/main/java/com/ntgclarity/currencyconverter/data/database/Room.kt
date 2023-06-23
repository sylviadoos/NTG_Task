package com.ntgclarity.currencyconverter.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ntgclarity.currencyconverter.modules.history.models.HistoryUiModel

@Dao
interface CurrenciesDao {

    // user List
    @Query("select dateConvert,group_concat((currencyFrom || ' / ' || currencyTo),'\n') as convertData  from DatabaseCurrenciesListItem where dateConvert between :fromDate and :toDate group by dateConvert order by dateConvert desc")
    fun getDatabaseCurrencies(fromDate :String,toDate:String): LiveData<List<HistoryUiModel>>

//    @Query("select dateConvert,group_concat(concat(currencyFrom,'/',currencyTo),',') as convertData  from DatabaseCurrenciesListItem where dateConvert between :fromDate and :toDate group by dateConvert order by dateConvert desc")
//    fun getDatabaseCurrencies(fromDate :String,toDate:String): LiveData<List<DatabaseCurrenciesListItem>>

    @Insert
    fun insertAll(currenciesListItem: DatabaseCurrenciesListItem)


 }

@Database(entities = [DatabaseCurrenciesListItem::class], version = 1, exportSchema = false)
abstract class CurrenciesDatabase : RoomDatabase() {
    abstract val currenciesDao: CurrenciesDao
}