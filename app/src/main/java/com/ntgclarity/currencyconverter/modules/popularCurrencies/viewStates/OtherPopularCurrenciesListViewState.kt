package com.ntgclarity.currencyconverter.modules.popularCurrencies.viewStates

import com.ntgclarity.currencyconverter.modules.popularCurrencies.models.OtherPopularCurrencyUiModel

sealed class OtherPopularCurrenciesListViewState {
    class ErrorOtherPopularCurrenciesListViewState(val message: String) :OtherPopularCurrenciesListViewState()
    class OtherPopularCurrenciesAvailableListViewState(val list:List<OtherPopularCurrencyUiModel>) :OtherPopularCurrenciesListViewState()

}
