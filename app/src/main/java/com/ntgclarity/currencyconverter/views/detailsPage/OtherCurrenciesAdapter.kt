package com.ntgclarity.currencyconverter.views.detailsPage


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ntgclarity.currencyconverter.databinding.ItemCrrencyListBinding
import com.ntgclarity.currencyconverter.databinding.ItemOthetCurrenciesBinding

import com.ntgclarity.currencyconverter.data.model.PopularCurrenciesItem
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class OtherCurrenciesAdapter @Inject constructor() :
    ListAdapter<PopularCurrenciesItem, OtherCurrenciesAdapter.ViewHolder>(OtherCurrenciesListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemOthetCurrenciesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PopularCurrenciesItem) {
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

class OtherCurrenciesListDiffCallback : DiffUtil.ItemCallback<PopularCurrenciesItem>() {

    override fun areItemsTheSame(oldItem: PopularCurrenciesItem, newItem: PopularCurrenciesItem): Boolean {
        return oldItem.otherCurrency == newItem.otherCurrency
    }

    override fun areContentsTheSame(oldItem: PopularCurrenciesItem, newItem: PopularCurrenciesItem): Boolean {
        return oldItem == newItem
    }

}