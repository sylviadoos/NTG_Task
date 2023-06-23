package com.ntgclarity.currencyconverter.modules.currencyConvert.viewStates

import com.ntgclarity.currencyconverter.modules.currencyConvert.models.CurrencyUiModel

sealed class CurrenciesListViewState {
    class ErrorCurrenciesListViewState(val message: String) :CurrenciesListViewState()
    class CurrenciesAvailableListViewState(val list:List<CurrencyUiModel>) :CurrenciesListViewState()

}
