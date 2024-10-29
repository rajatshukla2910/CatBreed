package com.catfeature.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.catfeature.data.CatApiData
import com.catfeature.ui.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso

class CatBreedsDataAdapter(
    val context: Context,
    val onClick: (CatApiData) -> Unit
) : ListAdapter<CatApiData, CatBreedsDataAdapter.CatBreedsDataViewHolder>(CatApiDataDiffCallback()) {

    class CatBreedsDataViewHolder(val binding: ItemLayoutBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

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
        Picasso.get()
            .load(item.image?.url)
            .into(holder.binding.imageView)

        holder.binding.apply {
            name.text = context.getString(R.string.breed, item.name)
            lifeSpan.text = context.getString(R.string.lifespan, item.life_span)
            origin.text =  context.getString(R.string.origin, item.origin)
        }
    }

    class CatApiDataDiffCallback : DiffUtil.ItemCallback<CatApiData>() {
        override fun areItemsTheSame(oldItem: CatApiData, newItem: CatApiData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatApiData, newItem: CatApiData): Boolean {
            return oldItem == newItem
        }
    }
}
