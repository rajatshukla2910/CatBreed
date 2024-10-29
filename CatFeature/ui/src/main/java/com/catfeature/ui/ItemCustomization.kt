package com.catfeature.ui

import android.content.Context
import com.catfeature.data.ListViewData
import com.squareup.picasso.Picasso

interface ItemCustomization {

    fun customize(
        holder: CatBreedsDataAdapter.CatBreedsDataViewHolder,
        config: ListConfig,
        data: ListViewData,
        context: Context
    )
}

class DefaultItemCustomization: ItemCustomization {
    override fun customize(
        holder: CatBreedsDataAdapter.CatBreedsDataViewHolder,
        config: ListConfig,
        data: ListViewData,
        context: Context
    ) {
        holder.binding.apply {
            //setting text color
            lifeSpan.setTextColor(config.textColor)
            origin.setTextColor(config.textColor)
            name.setTextColor(config.textColor)

        }

        Picasso.get()
            .load(data.image?.url)
            .placeholder(config.placeHolderImage)
            .into(holder.binding.imageView)

        holder.binding.apply {
            name.text = context.getString(R.string.breed, data.name)
            lifeSpan.text = context.getString(R.string.lifespan, data.life_span)
            origin.text =  context.getString(R.string.origin, data.origin)
        }
        config.backgroundColor.let { holder.binding.listItem.setBackgroundColor(it) }
        config.backgroundResource?.let { holder.binding.listItem.setBackgroundResource(it) }

    }
}