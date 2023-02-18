package com.swbvelasquez.nekoexpress.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback.ProductCategoryDiffUtilCallback
import com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder.ExposeProductCategoryViewHolder

class ExposeProductCategoryAdapter(
    private val onClickListener:(CategoryModel)->Unit)
    :ListAdapter<CategoryModel,ExposeProductCategoryViewHolder>(ProductCategoryDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ExposeProductCategoryViewHolder {
        return ExposeProductCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_expose_product_category,parent,false))
    }

    override fun onBindViewHolder(holder: ExposeProductCategoryViewHolder, position: Int) {
        holder.bind(getItem(position),onClickListener)
    }
}