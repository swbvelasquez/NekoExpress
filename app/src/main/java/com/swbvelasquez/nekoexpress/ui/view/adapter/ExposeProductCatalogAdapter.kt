package com.swbvelasquez.nekoexpress.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback.ProductCatalogDiffUtilCallback
import com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder.ExposeProductCatalogViewHolder

class ExposeProductCatalogAdapter(private val onClickListener:(ProductCatalogModel)->Unit
                                  , private val onClickFavoriteListener:(ProductCatalogModel)->Unit)
    : ListAdapter<ProductCatalogModel,ExposeProductCatalogViewHolder>(ProductCatalogDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExposeProductCatalogViewHolder {
        return ExposeProductCatalogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_expose_product_catalog,parent,false))
    }

    override fun onBindViewHolder(holder: ExposeProductCatalogViewHolder, position: Int) {
        holder.bind(getItem(position),onClickListener,onClickFavoriteListener)
    }
}