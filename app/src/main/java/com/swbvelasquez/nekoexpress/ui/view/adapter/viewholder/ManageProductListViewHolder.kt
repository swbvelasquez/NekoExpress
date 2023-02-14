package com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.databinding.ItemManageProductListBinding
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class ManageProductListViewHolder(view:View):ViewHolder(view) {
    private val binding = ItemManageProductListBinding.bind(view)
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
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvThumbnail)
        }
    }
}