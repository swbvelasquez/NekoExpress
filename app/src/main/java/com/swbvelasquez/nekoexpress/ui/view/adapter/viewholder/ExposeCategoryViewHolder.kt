package com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.swbvelasquez.nekoexpress.databinding.ItemExposeCategoryBinding
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel

class ExposeCategoryViewHolder(view: View):ViewHolder(view) {
    private val binding = ItemExposeCategoryBinding.bind(view)

    fun bind(category:CategoryModel, onClickListener:(CategoryModel)->Unit){
        with(binding){
            tvTitle.text = category.name
            root.setOnClickListener { onClickListener(category) }

            Glide
                .with(itemView.context)
                .load(category.id)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvThumbnail)
        }
    }
}