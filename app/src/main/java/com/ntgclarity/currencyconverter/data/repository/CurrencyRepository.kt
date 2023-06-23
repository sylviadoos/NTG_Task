package com.ntgclarity.currencyconverter.data.repository

import com.ntgclarity.currencyconverter.data.model.NetworkCurrencyListItem
import com.ntgclarity.currencyconverter.data.network.CurrenciesApis
import com.ntgclarity.currencyconverter.modules.BaseResponseUiModel
import javax.inject.Inject


class CurrencyRepository  @Inject constructor(private val api: CurrenciesApis
) {


     suspend fun getCurrencyCodes(accessKey:String): BaseResponseUiModel<Map<String, Double>> {

        val response = api.getCurrenciesList(accessKey)
        return BaseResponseUiModel(response.isSuccessful,response.message(),response.body()?.rates)
     }

}