package com.ntgclarity.currencyconverter.views.currencyList

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

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

     @SuppressLint("SuspiciousIndentation")
     fun convertAmount(fromCurrency:String, toCurrency:String, amount: Double, rate: Double) {
        afterConvertText.value= (amount*rate).toBigDecimal().setScale(3, RoundingMode.HALF_UP).toString()
        try {
            val currencyItem = CurrenciesListItem(LocalDate.now().toString(),fromCurrency,toCurrency)
            if(toCurrency!="")
            database.currenciesDao.insertAll(currencyItem.asDatabaseModel())
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

}