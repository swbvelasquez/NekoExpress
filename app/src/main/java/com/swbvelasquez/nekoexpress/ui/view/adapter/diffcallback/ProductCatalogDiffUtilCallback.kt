package com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

/**Clase u objecto para manejar las animaciones de forma automatica**/
class ProductCatalogDiffUtilCallback : DiffUtil.ItemCallback<ProductCatalogModel>() {
    override fun areItemsTheSame(oldItem: ProductCatalogModel, newItem: ProductCatalogModel): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ProductCatalogModel, newItem: ProductCatalogModel): Boolean = oldItem == newItem
}