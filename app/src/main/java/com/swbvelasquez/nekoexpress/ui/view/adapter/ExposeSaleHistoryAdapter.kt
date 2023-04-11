package com.swbvelasquez.nekoexpress.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback.CategoryDiffUtilCallback
import com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback.InvoiceDiffUtilCallback
import com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder.ExposeCategoryViewHolder
import com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder.ExposeSaleHistoryViewHolder

class ExposeSaleHistoryAdapter(
    private val onClickListener:(InvoiceModel)->Unit)
    :ListAdapter<InvoiceModel,ExposeSaleHistoryViewHolder>(InvoiceDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ExposeSaleHistoryViewHolder {
        return ExposeSaleHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_expose_sale_history,parent,false))
    }

    override fun onBindViewHolder(holder: ExposeSaleHistoryViewHolder, position: Int) {
        holder.bind(getItem(position),onClickListener)
    }
}