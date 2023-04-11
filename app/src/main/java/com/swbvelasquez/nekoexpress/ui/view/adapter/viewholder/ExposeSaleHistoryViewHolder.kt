package com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.ItemExposeSaleHistoryBinding
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel

class ExposeSaleHistoryViewHolder(view: View):ViewHolder(view) {
    private val binding = ItemExposeSaleHistoryBinding.bind(view)

    fun bind(invoice:InvoiceModel, onClickListener:(InvoiceModel)->Unit){
        with(binding){
            tvOrderNumber.text = String.format(itemView.context.getString(R.string.format_item_invoice_number),invoice.invoiceId)

            val purchaseDate = Functions.getFormattedDateFromLong(itemView.context.getString(R.string.format_date_default),invoice.date)
            tvPurchaseDate.text = String.format(itemView.context.getString(R.string.format_item_invoice_purchase_date),purchaseDate)

            tvTotal.text = String.format(itemView.context.getString(R.string.format_item_invoice_total),invoice.total)

            root.setOnClickListener { onClickListener(invoice) }
        }
    }
}