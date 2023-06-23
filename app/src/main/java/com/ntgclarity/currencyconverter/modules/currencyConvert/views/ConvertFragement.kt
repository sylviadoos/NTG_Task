package com.ntgclarity.currencyconverter.modules.currencyConvert.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ntgclarity.currencyconverter.R
import com.ntgclarity.currencyconverter.databinding.FragmentCurrenciesBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import com.ntgclarity.currencyconverter.views.currencyList.CurrenciesDirections
import com.ntgclarity.currencyconverter.modules.currencyConvert.viewModel.CurrencyViewModel
import com.ntgclarity.currencyconverter.modules.currencyConvert.viewStates.CurrenciesListViewState

@AndroidEntryPoint
class ConvertFragement : Fragment() {
    private val viewModel: CurrencyViewModel by viewModels()


    private var _binding: FragmentCurrenciesBinding? = null
    private val binding get() = _binding!!

    private var selectedFrom = ""
    private var selectedTo = ""
    private var positionFrom = 0
    private var positionTo = 0
    private var tempPositionSwap = 0
    private var converterRateFrom = 0.0
    private var converterRateTo = 0.0
    private var converterRate = 0.0
    private var amount = 1.0


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

        //check internet then call api to get data

            binding.internetConnection.visibility = View.VISIBLE
            binding.internetConnectionText.visibility = View.VISIBLE
            binding.convertLayout.visibility = View.GONE


//received data and load it into spinner

        //load data into first spinner Currency
        viewModel.currenciesViewStateLiveData.observe(viewLifecycleOwner, { currencyCodes ->
            when(currencyCodes)
            {
                is CurrenciesListViewState.ErrorCurrenciesListViewState ->
                    Toast.makeText(requireContext(), currencyCodes.message, Toast.LENGTH_SHORT).show()
                is CurrenciesListViewState.CurrenciesAvailableListViewState ->
                {
                    val fromCurrencyAdapter = ArrayAdapter(
                        binding.root.context,
                        android.R.layout.simple_spinner_item,
                        currencyCodes.keys.toList()
                    )
                    fromCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.fromCurrency.adapter = fromCurrencyAdapter
                    binding.fromCurrency.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                selectedFrom = binding.fromCurrency.selectedItem.toString()
                                positionFrom = binding.fromCurrency.selectedItemPosition
                                converterRateFrom = currencyCodes.values.elementAt(positionFrom)
                                viewModel.convertAmount(selectedFrom, selectedTo, amount, converterRate)


                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                            }
                        }

                    //load data into second spinner Currency that amount convert to it
                    val toCurrencyAdapter = ArrayAdapter(
                        binding.root.context,
                        android.R.layout.simple_spinner_item,
                        currencyCodes.keys.toList()
                    )
                    toCurrencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.toCurrency.adapter = toCurrencyAdapter
                    binding.toCurrency.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View,
                                position: Int,
                                id: Long
                            ) {
                                selectedTo = binding.toCurrency.selectedItem.toString()
                                positionTo = binding.toCurrency.selectedItemPosition
                                converterRateTo = currencyCodes.values.elementAt(positionTo)
                                converterRate = converterRateTo / converterRateFrom
                                viewModel.amount=it.toString().toDouble()


                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                            }
                        }

                }




            }

        })

        //click on swap button
        binding.convertSwap.setOnClickListener {
            tempPositionSwap = positionFrom
            positionFrom = positionTo
            positionTo = tempPositionSwap
            binding.toCurrency.setSelection(positionTo)
            binding.fromCurrency.setSelection(positionFrom)
            converterRate = converterRateFrom / converterRateTo
            viewModel.amount=it.toString().toDouble()


        }
        //default value amount
        binding.fromAmount.setText("1.0")

        //write amount to convert
        binding.fromAmount.doAfterTextChanged {
            viewModel.amount=it.toString().toDouble()

        }


        //click on details button
        binding.detailsOpen.setOnClickListener {
            findNavController().navigate(CurrenciesDirections.actionToDetails(selectedFrom))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
