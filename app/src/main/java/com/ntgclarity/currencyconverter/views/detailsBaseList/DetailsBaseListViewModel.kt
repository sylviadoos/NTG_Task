package com.ntgclarity.currencyconverter.views.detailsBaseList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntgclarity.currencyconverter.BuildConfig
import com.ntgclarity.currencyconverter.data.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.data.database.domain.CurrenciesListItem
import com.ntgclarity.currencyconverter.data.database.domain.asDatabaseModel
import com.ntgclarity.currencyconverter.data.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.RoundingMode
import java.sql.Date
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DetailsBaseListViewModel @Inject constructor(private val repository: CurrencyRepository,
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



}