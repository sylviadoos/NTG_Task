package com.ntgclarity.currencyconverter.views.currencyList

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntgclarity.currencyconverter.BuildConfig
import com.ntgclarity.currencyconverter.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.domain.CurrenciesListItem
import com.ntgclarity.currencyconverter.repository.CurrencyRepository
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Converter
import timber.log.Timber
import java.math.RoundingMode
import java.sql.Date
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: CurrencyRepository) : ViewModel() {
     val _currencyCodes = MutableLiveData<Map<String, Double>>()
    val currencyCodes: LiveData<Map<String, Double>> = _currencyCodes
    private var afterConvertText = MutableLiveData<String>()
    private val database: CurrenciesDatabase

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
            val currencyItem = CurrenciesListItem(fromCurrency,toCurrency)
            database.currenciesDao.insertAll(currencyItem.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }
}