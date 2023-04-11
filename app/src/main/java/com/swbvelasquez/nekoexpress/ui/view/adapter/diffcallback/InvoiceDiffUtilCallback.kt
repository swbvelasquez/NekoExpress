package com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel

class InvoiceDiffUtilCallback: DiffUtil.ItemCallback<InvoiceModel>(){
    override fun areItemsTheSame(oldItem: InvoiceModel, newItem: InvoiceModel): Boolean = oldItem.invoiceId == newItem.invoiceId

    override fun areContentsTheSame(oldItem: InvoiceModel, newItem: InvoiceModel): Boolean = oldItem == newItem
}