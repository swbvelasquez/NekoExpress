package com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.databinding.ItemCheckoutCartBinding
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel

class CheckoutCartViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val binding = ItemCheckoutCartBinding.bind(view)

    fun bind(product:ProductCartModel,
             onClickChangeQuantityListener:(ProductCartModel)->Unit,
             onClickDeleteListener:(ProductCartModel)->Unit){
        with(binding){
            tvTitle.text = product.title
            tvPrice.text = String.format(itemView.context.getString(R.string.format_product_price),product.price)

            if(product.size.isEmpty())
                tvSize.visibility = View.GONE
            else
                tvSize.text = String.format(itemView.context.getString(R.string.format_product_size),product.size)

            if(product.color.isEmpty())
                tvColor.visibility = View.GONE
            else
                tvColor.text = String.format(itemView.context.getString(R.string.format_product_color),product.color)

            tvQuantity.text = product.quantity.toString()

            fabAdd.setOnClickListener {
                product.apply {
                    quantity += 1
                    total = quantity * price
                }
                tvQuantity.text = product.quantity.toString()
                onClickChangeQuantityListener(product)
            }
            fabRemove.setOnClickListener {
                product.apply {
                    if(quantity>1) {
                        quantity -= 1
                        total = quantity * price
                    }
                }
                tvQuantity.text = product.quantity.toString()
                onClickChangeQuantityListener(product)
            }
            imvDelete.setOnClickListener {
                onClickDeleteListener(product)
            }

            Glide
                .with(itemView.context)
                .load(product.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvThumbnail)
        }
    }
}