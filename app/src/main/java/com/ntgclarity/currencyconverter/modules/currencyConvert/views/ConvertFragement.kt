package com.ntgclarity.currencyconverter.modules.currencyConvert.views

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.findNavController
import com.ntgclarity.currencyconverter.R
import com.ntgclarity.currencyconverter.databinding.FragmentConvertBinding
import com.ntgclarity.currencyconverter.modules.currencyConvert.models.CurrencyUiModel
import com.ntgclarity.currencyconverter.modules.currencyConvert.viewModel.CurrencyViewModel
import com.ntgclarity.currencyconverter.modules.currencyConvert.viewStates.CurrenciesListViewState
import com.ntgclarity.currencyconverter.networkCheck.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConvertFragement : Fragment() {
    private val viewModel: CurrencyViewModel by viewModels()


    private var _binding: FragmentConvertBinding? = null
    private val binding get() = _binding!!

    private var selectedFrom = ""
    private var positionFrom = 0
    private var positionTo = 0
    private var tempPositionSwap = 0
    private lateinit var connectionLiveData : ConnectionLiveData


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_convert, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //check internet then call api to get data

        connectionLiveData = ConnectionLiveData(binding.root.context)

        binding.lifecycleOwner?.let {
            connectionLiveData.observe(it, { isConnected ->

                if (isConnected){
                   viewModel.fetchCurrencyCodes()
                    binding.internetConnection.visibility = View.GONE
                    binding.internetConnectionText.visibility = View.GONE
                    binding.convertLayout.visibility = View.VISIBLE

                }else{
                    binding.internetConnection.visibility = View.VISIBLE
                    binding.internetConnectionText.visibility = View.VISIBLE
                    binding.convertLayout.visibility = View.GONE

                }

            })
        }



//received data and load it into spinner

        //load data into first spinner Currency
        viewModel.currenciesViewStateLiveData.observe(viewLifecycleOwner, { currencyCodes ->
            when (currencyCodes) {
                is CurrenciesListViewState.ErrorCurrenciesListViewState ->
                    Toast.makeText(requireContext(), currencyCodes.message, Toast.LENGTH_SHORT)
                        .show()

                is CurrenciesListViewState.CurrenciesAvailableListViewState -> {
                    val fromCurrencyAdapter = ArrayAdapter(
                        binding.root.context,
                        android.R.layout.simple_spinner_item,
                        currencyCodes.list
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
                                positionFrom = binding.fromCurrency.selectedItemPosition
                                viewModel.selectedFromCurrency = CurrencyUiModel(
                                    currencyCodes.list.get(positionFrom).name,
                                    currencyCodes.list.get(positionFrom).rate
                                )
                                viewModel.amount = binding.fromAmount.text.toString().toDouble()


                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {

                            }
                        }

                    //load data into second spinner Currency that amount convert to it
                    val toCurrencyAdapter = ArrayAdapter(
                        binding.root.context,
                        android.R.layout.simple_spinner_item,
                        currencyCodes.list
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
                                positionTo = binding.toCurrency.selectedItemPosition
                                viewModel.selectedToCurrency = CurrencyUiModel(
                                    currencyCodes.list.get(positionTo).name,
                                    currencyCodes.list.get(positionTo).rate
                                )
                                viewModel.amount = binding.fromAmount.text.toString().toDouble()


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
            viewModel.amount = binding.fromAmount.text.toString().toDouble()


        }
        //default value amount
        binding.fromAmount.setText("1.0")

        //write amount to convert
        binding.fromAmount.doAfterTextChanged {
            if (it.toString() != "")
                viewModel.amount = it.toString().toDouble()

        }


        //click on details button
        binding.detailsOpen.setOnClickListener {
            findNavController().navigate(ConvertFragementDirections.actionToDetails(viewModel.selectedFromCurrency.name,viewModel.amount.toString(),viewModel.selectedFromCurrency.rate.toString()))
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
