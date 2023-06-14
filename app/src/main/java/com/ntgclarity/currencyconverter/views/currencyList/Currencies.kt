package com.ntgclarity.currencyconverter.views.currencyList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ntgclarity.currencyconverter.R
import com.ntgclarity.currencyconverter.databinding.FragmentCurrenciesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Currencies : Fragment() {
    private val viewModel: CurrencyViewModel by viewModels()


    private var _binding: FragmentCurrenciesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_currencies, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCurrencyCodes()


        viewModel.currencyCodes.observe(viewLifecycleOwner, { currencyCodes ->
            val fromCurrencyAdapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_item, currencyCodes.keys.toList())
            fromCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.fromCurrency.adapter = fromCurrencyAdapter
            //
            val toCurrencyAdapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_item, currencyCodes.keys.toList())
            toCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.toCurrency.adapter = toCurrencyAdapter
        })
     //   Log.d("MyTag",currencyCodes.keys.toString())





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}