package com.ntgclarity.currencyconverter.modules.popularCurrencies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntgclarity.currencyconverter.BuildConfig
import com.ntgclarity.currencyconverter.data.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.data.repository.CurrenciesAvailableRepository
import com.ntgclarity.currencyconverter.data.repository.OtherPopularRepository
import com.ntgclarity.currencyconverter.modules.currencyConvert.models.CurrencyUiModel
import com.ntgclarity.currencyconverter.modules.currencyConvert.viewStates.CurrenciesListViewState
import com.ntgclarity.currencyconverter.modules.popularCurrencies.models.OtherPopularCurrencyUiModel
import com.ntgclarity.currencyconverter.modules.popularCurrencies.viewStates.OtherPopularCurrenciesListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


import javax.inject.Inject

@HiltViewModel
class OtherPopularCurrenciesListViewModel @Inject constructor(
    private val repository: OtherPopularRepository,
    private val database: CurrenciesDatabase
) : ViewModel() {
    private val _currenciesViewStateLiveData = MutableLiveData<OtherPopularCurrenciesListViewState>()
    val currenciesViewStateLiveData: LiveData<OtherPopularCurrenciesListViewState> =
        _currenciesViewStateLiveData


    fun fetchCurrencyCodes(symbols:List<String>,base : String,baseRate : Double,amont : Double) {
        viewModelScope.launch {
            val codes = repository.getCurrencyCodes(BuildConfig.API_KEY)
            if (codes.isSuccessful && codes.data != null) {
                if(codes.data.success &&  codes.data.rates != null){
                    _currenciesViewStateLiveData.value =
                        OtherPopularCurrenciesListViewState.OtherPopularCurrenciesAvailableListViewState(
                            codes.data.rates.map {
                                OtherPopularCurrencyUiModel(it.key, amont*(it.value/baseRate))
                            }.filter { (symbols.contains(it.name)) && (it.name != base)   }.toList()

                    )

                }else{
                    _currenciesViewStateLiveData.value =
                        OtherPopularCurrenciesListViewState.ErrorOtherPopularCurrenciesListViewState(codes.data.error?.info.toString())

                }

            } else {
                _currenciesViewStateLiveData.value =
                    OtherPopularCurrenciesListViewState.ErrorOtherPopularCurrenciesListViewState(codes.message)
            }
        }
    }


}