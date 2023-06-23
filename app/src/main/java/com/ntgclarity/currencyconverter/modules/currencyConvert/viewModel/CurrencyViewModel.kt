package com.ntgclarity.currencyconverter.modules.currencyConvert.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntgclarity.currencyconverter.BuildConfig
import com.ntgclarity.currencyconverter.data.database.CurrenciesDatabase
import com.ntgclarity.currencyconverter.data.database.DatabaseCurrenciesListItem
import com.ntgclarity.currencyconverter.data.repository.CurrencyRepository
import com.ntgclarity.currencyconverter.modules.currencyConvert.models.CurrencyUiModel
import com.ntgclarity.currencyconverter.modules.currencyConvert.viewStates.CurrenciesListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.math.RoundingMode
import java.time.LocalDate

import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val database: CurrenciesDatabase
) : ViewModel() {
   private val _currenciesViewStateLiveData = MutableLiveData<CurrenciesListViewState>()
    val currenciesViewStateLiveData: LiveData<CurrenciesListViewState> = _currenciesViewStateLiveData
    private val _amountCurrencyToLiveData = MutableLiveData<String>()
    val amountCurrencyToLiveData: LiveData<String>
        get() = _amountCurrencyToLiveData

    var selectedFromCurrency = CurrencyUiModel("",1.0)
        set(value) {
            field = value
            convertAmount()
        }
    var selectedToCurrency = CurrencyUiModel("",1.0)
        set(value) {
            field = value
            convertAmount()
        }
    var amount = 1.0
        set(value) {
            field = value
            convertAmount()
        }



    fun fetchCurrencyCodes() {
        viewModelScope.launch {
            val codes = repository.getCurrencyCodes(BuildConfig.API_KEY)
            if (codes.isSuccessful && codes.data != null) {
                _currenciesViewStateLiveData.value = CurrenciesListViewState.CurrenciesAvailableListViewState(
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

    @SuppressLint("SuspiciousIndentation")
    fun convertAmount() {
        _amountCurrencyToLiveData.value =
            (amount * (selectedToCurrency.rate/selectedFromCurrency.rate)).toBigDecimal().setScale(3, RoundingMode.HALF_UP).toString()
        try {
            val currencyItem = DatabaseCurrenciesListItem(
                dateConvert = LocalDate.now().toString(),
                currencyFrom = selectedFromCurrency.name,
                currencyTo = selectedToCurrency.name
            )
            if (selectedFromCurrency.name != "" && !selectedFromCurrency.name.equals(selectedToCurrency.name))
                database.currenciesDao.insertAll(currencyItem)
        } catch (e: Exception) {
            Timber.w(e)
        }
    }

}