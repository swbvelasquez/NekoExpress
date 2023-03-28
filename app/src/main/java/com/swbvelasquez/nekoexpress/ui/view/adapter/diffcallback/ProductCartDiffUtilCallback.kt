package com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel

class ProductCartDiffUtilCallback : DiffUtil.ItemCallback<ProductCartModel>() {
    override fun areItemsTheSame(oldItem: ProductCartModel, newItem: ProductCartModel): Boolean =
        oldItem.productId == newItem.productId && oldItem.cartId == newItem.cartId && oldItem.productCartId == newItem.productCartId

    override fun areContentsTheSame(oldItem: ProductCartModel, newItem: ProductCartModel): Boolean = oldItem == newItem
}