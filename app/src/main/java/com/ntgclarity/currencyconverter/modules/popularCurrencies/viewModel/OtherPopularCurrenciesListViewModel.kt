package com.ntgclarity.currencyconverter.modules.popularCurrencies.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntgclarity.currencyconverter.BuildConfig
import com.ntgclarity.currencyconverter.data.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.data.repository.CurrencyRepository
import com.ntgclarity.currencyconverter.modules.currencyConvert.models.CurrencyUiModel
import com.ntgclarity.currencyconverter.modules.currencyConvert.viewStates.CurrenciesListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


import javax.inject.Inject

@HiltViewModel
class OtherPopularCurrenciesListViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val database: CurrenciesDatabase
) : ViewModel() {
    private val _currenciesViewStateLiveData = MutableLiveData<CurrenciesListViewState>()
    val currenciesViewStateLiveData: LiveData<CurrenciesListViewState> =
        _currenciesViewStateLiveData


    fun fetchCurrencyCodes() {
        viewModelScope.launch {
            val codes = repository.getCurrencyCodes(BuildConfig.API_KEY)
            if (codes.isSuccessful && codes.data != null) {
                _currenciesViewStateLiveData.value =
                    CurrenciesListViewState.CurrenciesAvailableListViewState(
                        codes.data.map {
                            CurrencyUiModel(it.key, it.value)
                        }.toList()
                    )

            } else {
                _currenciesViewStateLiveData.value =
                    CurrenciesListViewState.ErrorCurrenciesListViewState(codes.message)
            }
        }
    }


}