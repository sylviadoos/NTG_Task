package com.ntgclarity.currencyconverter.modules.popularCurrencies.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ntgclarity.currencyconverter.R
import com.ntgclarity.currencyconverter.databinding.FragmentOtherPopularCurrenciesListBinding
import com.ntgclarity.currencyconverter.modules.popularCurrencies.viewModel.OtherPopularCurrenciesListViewModel
import com.ntgclarity.currencyconverter.modules.popularCurrencies.viewStates.OtherPopularCurrenciesListViewState
import com.ntgclarity.currencyconverter.views.adapters.OtherCurrenciesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OtherPopularCurrenciesFragment() : Fragment() {
    private val viewModel: OtherPopularCurrenciesListViewModel by viewModels()
     private val args: OtherPopularCurrenciesFragmentArgs by navArgs()
    private val symbols : List<String> = listOf("GBP","USD","EUR","CAD","AUD","CHF","BHD","KWD","OMR","KYD","JOD")
    private var _binding: FragmentOtherPopularCurrenciesListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: OtherCurrenciesAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_other_popular_currencies_list, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCurrencyCodes(symbols,"EUR",2.00,2.00)

        viewModel.currenciesViewStateLiveData.observe(viewLifecycleOwner, { currencyCodes ->
            when (currencyCodes) {
                is OtherPopularCurrenciesListViewState.ErrorOtherPopularCurrenciesListViewState ->
                    Toast.makeText(requireContext(), currencyCodes.message, Toast.LENGTH_SHORT)
                        .show()

                is OtherPopularCurrenciesListViewState.OtherPopularCurrenciesAvailableListViewState -> {
                    adapter.submitList(currencyCodes.list)
                }


            }})
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

}