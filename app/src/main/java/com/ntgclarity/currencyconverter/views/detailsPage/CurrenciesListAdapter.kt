package com.ntgclarity.currencyconverter.views.detailsPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ntgclarity.currencyconverter.databinding.ItemCrrencyListBinding

import com.ntgclarity.currencyconverter.data.database.domain.CurrenciesListItem
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@FragmentScoped
class CurrenciesListAdapter @Inject constructor() :
    ListAdapter<CurrenciesListItem, CurrenciesListAdapter.ViewHolder>(CurrenciesListDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemCrrencyListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CurrenciesListItem) {
            binding.data = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCrrencyListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CurrenciesListDiffCallback : DiffUtil.ItemCallback<CurrenciesListItem>() {

    override fun areItemsTheSame(oldItem: CurrenciesListItem, newItem: CurrenciesListItem): Boolean {
        return oldItem.currencyFrom == newItem.currencyFrom
    }

    override fun areContentsTheSame(oldItem: CurrenciesListItem, newItem: CurrenciesListItem): Boolean {
        return oldItem == newItem
    }

}

