package com.swbvelasquez.nekoexpress.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.domain.model.InvoiceDetailModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback.InvoiceDetailDiffUtilCallback
import com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback.ProductCartDiffUtilCallback
import com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder.CheckoutCartViewHolder
import com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder.InvoiceDetailViewHolder

class InvoiceDetailAdapter() : ListAdapter<InvoiceDetailModel,InvoiceDetailViewHolder>(InvoiceDetailDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceDetailViewHolder {
        return InvoiceDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_invoice_detail,parent,false))
    }

    override fun onBindViewHolder(holder: InvoiceDetailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}