package com.swbvelasquez.nekoexpress.ui.view.viewholder

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.databinding.ItemCatalogProductBinding
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class ProductCatalogViewHolder(view:View):ViewHolder(view) {
    private val binding = ItemCatalogProductBinding.bind(view)
    private var isAdded:Boolean = false

    fun bind(product: ProductCatalogModel, onClickAddListener:(ProductCatalogModel)->Unit){
        with(binding){
            tvTitle.text = product.title
            tvCategory.text = product.category
            tvPrice.text = String.format(itemView.context.getString(R.string.format_product_price),product.price)
            tvScore.text = product.rating.count.toString()
            rtbScore.rating = product.rating.rate.toFloat()

            btnAdd.setOnClickListener {
                if(isAdded){
                    isAdded=false
                    btnAdd.setImageResource(R.drawable.ic_add)
                    Toast.makeText(binding.root.context,"Product ${product.title} removed",Toast.LENGTH_SHORT).show()
                }else{
                    isAdded=true
                    btnAdd.setImageResource(R.drawable.ic_delete_trash)
                    Toast.makeText(binding.root.context,"Product ${product.title} added",Toast.LENGTH_SHORT).show()
                }

                onClickAddListener(product)
            }

            Glide
                .with(itemView.context)
                .load(product.image)
                .transform(CenterCrop(),RoundedCorners(24))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvThumbnail)
        }
    }
}