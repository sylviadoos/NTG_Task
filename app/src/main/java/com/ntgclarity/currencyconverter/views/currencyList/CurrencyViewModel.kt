package com.ntgclarity.currencyconverter.views.currencyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntgclarity.currencyconverter.repository.CurrencyRepository
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: CurrencyRepository) : ViewModel() {
     val _currencyCodes = MutableLiveData<Map<String, Double>>()
    val currencyCodes: LiveData<Map<String, Double>> = _currencyCodes

     fun fetchCurrencyCodes() {
        viewModelScope.launch {
            val codes = repository.getCurrencyCodes()
            _currencyCodes.value = codes
        }
    }
}