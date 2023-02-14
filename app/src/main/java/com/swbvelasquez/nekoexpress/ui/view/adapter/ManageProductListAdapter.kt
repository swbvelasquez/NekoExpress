package com.swbvelasquez.nekoexpress.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder.ManageProductListViewHolder

class ManageProductListAdapter(
    private val onClickAddListener:(ProductCatalogModel)->Unit)
    :ListAdapter<ProductCatalogModel, ManageProductListViewHolder>(ProductCatalogDiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageProductListViewHolder {
        return ManageProductListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_manage_product_list,parent,false))
    }

    override fun onBindViewHolder(holder: ManageProductListViewHolder, position: Int) {
        holder.bind(getItem(position),onClickAddListener)
    }

    /**Clase u objecto para manejar las animaciones de forma automatica**/
    private object ProductCatalogDiffUtilCallback : DiffUtil.ItemCallback<ProductCatalogModel>(){
        override fun areItemsTheSame(oldItem: ProductCatalogModel, newItem: ProductCatalogModel): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: ProductCatalogModel, newItem: ProductCatalogModel): Boolean = oldItem == newItem
    }


}