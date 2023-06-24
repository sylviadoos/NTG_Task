package com.ntgclarity.currencyconverter.views.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ntgclarity.currencyconverter.databinding.ItemOthetCurrenciesBinding

import com.ntgclarity.currencyconverter.modules.popularCurrencies.models.OtherPopularCurrencyUiModel
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class OtherCurrenciesAdapter @Inject constructor() :
    ListAdapter<OtherPopularCurrencyUiModel, OtherCurrenciesAdapter.ViewHolder>(
        OtherCurrenciesListDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemOthetCurrenciesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OtherPopularCurrencyUiModel) {
            binding.data = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemOthetCurrenciesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OtherCurrenciesListDiffCallback : DiffUtil.ItemCallback<OtherPopularCurrencyUiModel>() {

    override fun areItemsTheSame(oldItem: OtherPopularCurrencyUiModel, newItem: OtherPopularCurrencyUiModel): Boolean {
        return oldItem.name == newItem.name
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: OtherPopularCurrencyUiModel, newItem: OtherPopularCurrencyUiModel): Boolean {
        return oldItem == newItem
    }

}