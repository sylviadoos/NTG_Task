package com.ntgclarity.currencyconverter.views.currencyList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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

    private var selectedFrom=""
    private var selectedTo=""
    private var positionFrom=0
    private var positionTo=0
    private var tempPositionSwap=0
    private var converterRateFrom=0.0
    private var converterRateTo=0.0
    private var converterRate=0.0
    private var amount=1.0


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
            binding.fromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                   // Log.d("selected_data",binding.fromCurrency.selectedItem.toString())
                    selectedFrom=binding.fromCurrency.selectedItem.toString()
                    positionFrom=binding.fromCurrency.selectedItemPosition
                    converterRateFrom=currencyCodes.values.elementAt(positionFrom)


                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

            //
            val toCurrencyAdapter = ArrayAdapter(binding.root.context, android.R.layout.simple_spinner_item, currencyCodes.keys.toList())
            toCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.toCurrency.adapter = toCurrencyAdapter
            binding.toCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedTo=binding.toCurrency.selectedItem.toString()
                    positionTo=binding.toCurrency.selectedItemPosition
                    converterRateTo=currencyCodes.values.elementAt(positionTo)
                    converterRate=converterRateTo/converterRateFrom
                    viewModel.convertAmount(amount.toString().toDouble(),converterRate)

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        })

        binding.convertSwap.setOnClickListener {
            tempPositionSwap=positionFrom
            positionFrom=positionTo
            positionTo=tempPositionSwap
            binding.toCurrency.setSelection(positionTo)
            binding.fromCurrency.setSelection(positionFrom)
            converterRate=converterRateFrom/converterRateTo
            viewModel.convertAmount(amount.toString().toDouble(),converterRate)

        }

        binding.fromAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                amount=s.toString().toDouble();
                viewModel.convertAmount(amount,converterRate)
            }

            override fun afterTextChanged(s: Editable?) {
                // do nothing
            }
        })



     //   Log.d("MyTag",currencyCodes.keys.toString())





    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}