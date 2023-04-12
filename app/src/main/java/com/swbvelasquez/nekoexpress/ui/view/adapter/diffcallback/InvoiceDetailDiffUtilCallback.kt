package com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.swbvelasquez.nekoexpress.domain.model.InvoiceDetailModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel

class InvoiceDetailDiffUtilCallback : DiffUtil.ItemCallback<InvoiceDetailModel>() {
    override fun areItemsTheSame(oldItem: InvoiceDetailModel, newItem: InvoiceDetailModel): Boolean =
        oldItem.productId == newItem.productId && oldItem.invoiceId == newItem.invoiceId && oldItem.invoiceDetailId == newItem.invoiceDetailId

    override fun areContentsTheSame(oldItem: InvoiceDetailModel, newItem: InvoiceDetailModel): Boolean = oldItem == newItem
}