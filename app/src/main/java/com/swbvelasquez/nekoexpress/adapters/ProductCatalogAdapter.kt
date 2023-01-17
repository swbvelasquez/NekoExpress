package com.swbvelasquez.nekoexpress.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.models.ProductCatalogModel

class ProductCatalogAdapter(
    private val onClickAddListener:(ProductCatalogModel)->Unit)
    :ListAdapter<ProductCatalogModel, ProductCatalogViewHolder>(ProductCatalogDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCatalogViewHolder {
        return ProductCatalogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false))
    }

    override fun onBindViewHolder(holder: ProductCatalogViewHolder, position: Int) {
        holder.bind(getItem(position),onClickAddListener)
    }

    /**Clase u objecto para manejar las animaciones de forma automatica**/
    private object ProductCatalogDiffUtilCallback : DiffUtil.ItemCallback<ProductCatalogModel>(){
        override fun areItemsTheSame(oldItem: ProductCatalogModel, newItem: ProductCatalogModel): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: ProductCatalogModel, newItem: ProductCatalogModel): Boolean = oldItem == newItem
    }


}