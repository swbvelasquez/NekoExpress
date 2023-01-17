package com.swbvelasquez.nekoexpress.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.models.ProductCatalogModel

class ProductCatalogAdapter(
    private var productList:MutableList<ProductCatalogModel>,
    private val onClickAddListener:(ProductCatalogModel)->Unit)
    :RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position],onClickAddListener)
    }

    override fun getItemCount(): Int = productList.size
}