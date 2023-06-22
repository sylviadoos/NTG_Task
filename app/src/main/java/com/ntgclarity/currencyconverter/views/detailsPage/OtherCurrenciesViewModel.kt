package com.ntgclarity.currencyconverter.views.detailsPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntgclarity.currencyconverter.BuildConfig
import com.ntgclarity.currencyconverter.network.model.PopularCurrenciesItem
import com.ntgclarity.currencyconverter.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class OtherCurrenciesViewModel @Inject constructor(private val repository: CurrencyRepository,

                                                   ) : ViewModel() {
     val _currencyCodes = MutableLiveData<List<PopularCurrenciesItem>>()
    val currencyCodes: LiveData<List<PopularCurrenciesItem>> = _currencyCodes



    fun fetchCurrencyCodes(baseRate:Double,convertAmount:Double) {
        viewModelScope.launch {
            val codes = repository.getCurrencyCodes(BuildConfig.API_KEY)
            val myMutableDataList = mutableListOf<PopularCurrenciesItem>()

            for (item in codes) {
                if(myMutableDataList.size != 10){
                    val popularCurrenciesItem = PopularCurrenciesItem(item.key,((item.value/baseRate)*convertAmount).toString())
                    myMutableDataList.add(popularCurrenciesItem)
                }

            }
            _currencyCodes.value=myMutableDataList


        }
    }



}