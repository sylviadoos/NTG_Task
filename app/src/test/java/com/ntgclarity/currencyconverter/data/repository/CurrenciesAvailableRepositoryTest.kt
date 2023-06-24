package com.ntgclarity.currencyconverter.data.repository

import android.net.Network
import android.net.wifi.WifiManager.AddNetworkResult
import com.ntgclarity.currencyconverter.BuildConfig
import com.ntgclarity.currencyconverter.data.model.NetworkCurrencyListItem
import com.ntgclarity.currencyconverter.data.network.CurrenciesApis
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class CurrenciesAvailableRepositoryTest {

    @Mock
    lateinit var currenciesApis : CurrenciesApis

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCurrenciesEmptyList() = runTest {
        Mockito.`when`(currenciesApis.getCurrenciesList(BuildConfig.API_KEY)).thenReturn(Response.success(
            NetworkCurrencyListItem(success = true, rates =  emptyMap(),error = null)

        ))

        val sut = CurrenciesAvailableRepository(currenciesApis)
        val result = sut.getCurrencyCodes(BuildConfig.API_KEY)
        Assert.assertEquals(0, result.data!!.rates!!.size)

    }


}