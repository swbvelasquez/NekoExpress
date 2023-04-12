package com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.databinding.ItemInvoiceDetailBinding
import com.swbvelasquez.nekoexpress.domain.model.InvoiceDetailModel

class InvoiceDetailViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = ItemInvoiceDetailBinding.bind(view)

    fun bind(invoiceDetail:InvoiceDetailModel){
        with(binding){
            tvTitle.text = invoiceDetail.title
            tvPrice.text = String.format(itemView.context.getString(R.string.format_item_invoice_detail_price),invoiceDetail.price)

            if(invoiceDetail.size.isEmpty())
                tvSize.visibility = View.GONE
            else
                tvSize.text = String.format(itemView.context.getString(R.string.format_item_invoice_detail_size),invoiceDetail.size)

            if(invoiceDetail.color.isEmpty())
                tvColor.visibility = View.GONE
            else
                tvColor.text = String.format(itemView.context.getString(R.string.format_item_invoice_detail_color),invoiceDetail.color)

            tvQuantity.text = String.format(itemView.context.getString(R.string.format_item_invoice_detail_quantity),invoiceDetail.quantity)

            Glide
                .with(itemView.context)
                .load(invoiceDetail.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvThumbnail)
        }
    }
}