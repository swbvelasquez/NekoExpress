package com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.databinding.ItemExposeFavoriteProductBinding
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class ExposeFavoriteProductViewHolder(view:View):ViewHolder(view) {
    private val binding = ItemExposeFavoriteProductBinding.bind(view)


    fun bind(product:ProductCatalogModel,onClickListener:(ProductCatalogModel)->Unit, onClickFavoriteListener:(ProductCatalogModel)->Unit){
        with(binding){
            tvTitle.text = product.title
            tvPrice.text = String.format(itemView.context.getString(R.string.format_item_product_price),product.price)

            imvFavorite.setOnClickListener {
                onClickFavoriteListener(product)
            }

            imvThumbnail.setOnClickListener {
                onClickListener(product)
            }
            tvTitle.setOnClickListener {
                onClickListener(product)
            }
            tvPrice.setOnClickListener {
                onClickListener(product)
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