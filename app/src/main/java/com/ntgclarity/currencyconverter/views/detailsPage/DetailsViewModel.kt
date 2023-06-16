package com.ntgclarity.currencyconverter.views.detailsPage

import androidx.lifecycle.ViewModel
import com.ntgclarity.currencyconverter.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: DetailsRepository,
) : ViewModel() {

    val data = repository.users



}