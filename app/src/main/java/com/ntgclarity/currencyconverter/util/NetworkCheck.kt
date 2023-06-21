package com.ntgclarity.currencyconverter.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkCheck @Inject constructor(private val context: Context){
     fun isInternetConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}