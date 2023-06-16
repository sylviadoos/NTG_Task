package com.ntgclarity.currencyconverter.views.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntgclarity.currencyconverter.BuildConfig
import com.ntgclarity.currencyconverter.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.domain.CurrenciesListItem
import com.ntgclarity.currencyconverter.domain.asDatabaseModel
import com.ntgclarity.currencyconverter.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.RoundingMode
import java.sql.Date
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: CurrencyRepository,
                                            private val database: CurrenciesDatabase
) : ViewModel() {
     val _currencyCodes = MutableLiveData<Map<String, Double>>()
    val currencyCodes: LiveData<Map<String, Double>> = _currencyCodes
    private var afterConvertText = MutableLiveData<String>()


    val afterConvertAmount: LiveData<String>
        get() = afterConvertText

     fun fetchCurrencyCodes() {
        viewModelScope.launch {
            val codes = repository.getCurrencyCodes(BuildConfig.API_KEY)
            _currencyCodes.value = codes
        }
    }

     fun convertAmount(fromCurrency:String,toCurrency:String,amount: Double,rate: Double) {
        afterConvertText.value= (amount*rate).toBigDecimal().setScale(3, RoundingMode.HALF_UP).toString()
        try {
            val currencyItem = CurrenciesListItem(Date(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_WEEK).toString(),fromCurrency,toCurrency)
            database.currenciesDao.insertAll(currencyItem.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

}