package com.ntgclarity.currencyconverter.modules.currencyConvert.models

data class CurrencyUiModel (val name:String,val rate:Double){
    override fun toString(): String {
        return name
    }
}
