package com.swbvelasquez.nekoexpress.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.diffcallback.ProductCartDiffUtilCallback
import com.swbvelasquez.nekoexpress.ui.view.adapter.viewholder.CheckoutCartViewHolder

class CheckoutCartAdapter(
    private val onClickChangeQuantityListener:(ProductCartModel)->Unit,
    private val onClickDeleteListener:(ProductCartModel)->Unit)
    : ListAdapter<ProductCartModel,CheckoutCartViewHolder>(ProductCartDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutCartViewHolder {
        return CheckoutCartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_checkout_cart,parent,false))
    }

    override fun onBindViewHolder(holder: CheckoutCartViewHolder, position: Int) {
        holder.bind(getItem(position),onClickChangeQuantityListener,onClickDeleteListener)
    }
}