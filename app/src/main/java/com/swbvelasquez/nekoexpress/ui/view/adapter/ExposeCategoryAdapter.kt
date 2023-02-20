package com.swbvelasquez.nekoexpress.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback.CategoryDiffUtilCallback
import com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder.ExposeCategoryViewHolder

class ExposeCategoryAdapter(
    private val onClickListener:(CategoryModel)->Unit)
    :ListAdapter<CategoryModel,ExposeCategoryViewHolder>(CategoryDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ExposeCategoryViewHolder {
        return ExposeCategoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_expose_category,parent,false))
    }

    override fun onBindViewHolder(holder: ExposeCategoryViewHolder, position: Int) {
        holder.bind(getItem(position),onClickListener)
    }
}