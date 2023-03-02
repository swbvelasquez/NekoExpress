package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.model.CartWithProductDto

data class CartModel(
    val id:Int,
    val userId:Int,
    val productList:MutableList<ProductCartModel>
)

fun CartWithProductDto.toCartModel() = CartModel(id=cart.id, userId = cart.userId, productList = productList.map {it.toProductCartModel()}.toMutableList())