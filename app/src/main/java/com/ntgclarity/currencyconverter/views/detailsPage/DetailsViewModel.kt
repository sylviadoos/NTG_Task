package com.ntgclarity.currencyconverter.views.detailsPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ntgclarity.currencyconverter.data.repository.DetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
) : ViewModel() {
    private val _currentFragmentId = MutableLiveData<Int>()
    val currentFragmentId: LiveData<Int>
        get() = _currentFragmentId

    fun setCurrentFragmentId(fragmentId: Int) {
        _currentFragmentId.value = fragmentId
    }
}