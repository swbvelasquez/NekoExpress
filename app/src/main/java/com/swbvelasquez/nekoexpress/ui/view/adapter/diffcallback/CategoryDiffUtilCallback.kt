package com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel

class CategoryDiffUtilCallback: DiffUtil.ItemCallback<CategoryModel>(){
    override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean = oldItem.categoryId == newItem.categoryId

    override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean = oldItem == newItem
}