package com.catfeature.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.catfeature.data.ListViewData
import com.catfeature.ui.databinding.ItemLayoutBinding

class CatBreedsDataAdapter(
    val context: Context,
    val config: ListConfig,
    val itemCustomization: ItemCustomization =  DefaultItemCustomization(),
    val onClick: (ListViewData) -> Unit
) : ListAdapter<ListViewData, CatBreedsDataAdapter.CatBreedsDataViewHolder>(CatApiDataDiffCallback()) {

   inner class CatBreedsDataViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(data: ListViewData) {
                itemCustomization.customize(this, config, data, context)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatBreedsDataViewHolder {
        val binding = DataBindingUtil.inflate<ItemLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_layout,
            parent,
            false
        )
        return CatBreedsDataViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CatBreedsDataViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
        holder.bind(item)
    }

    class CatApiDataDiffCallback : DiffUtil.ItemCallback<ListViewData>() {
        override fun areItemsTheSame(oldItem: ListViewData, newItem: ListViewData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListViewData, newItem: ListViewData): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.origin == newItem.origin &&
                    oldItem.life_span == newItem.life_span &&
                    oldItem.image == newItem.image
        }
    }
}
