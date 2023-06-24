package com.ntgclarity.currencyconverter.data.repository

import com.ntgclarity.currencyconverter.data.model.NetworkCurrencyListItem
import com.ntgclarity.currencyconverter.data.network.CurrenciesApis
import com.ntgclarity.currencyconverter.modules.BaseResponseUiModel
import javax.inject.Inject

class OtherPopularRepository  @Inject constructor(private val api: CurrenciesApis
) {


    suspend fun getCurrencyCodes(accessKey:String): BaseResponseUiModel<NetworkCurrencyListItem> {

        val response = api.getCurrenciesList(accessKey)
        return BaseResponseUiModel(response.isSuccessful,response.message(),response.body())
    }

}