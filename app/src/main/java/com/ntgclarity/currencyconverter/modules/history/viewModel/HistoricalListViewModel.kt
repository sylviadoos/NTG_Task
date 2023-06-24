package com.ntgclarity.currencyconverter.modules.history.viewModel

import androidx.lifecycle.ViewModel
import com.ntgclarity.currencyconverter.data.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoricalListViewModel @Inject constructor(private val repository: DetailsRepository,
) : ViewModel() {

    val data = repository.history



}