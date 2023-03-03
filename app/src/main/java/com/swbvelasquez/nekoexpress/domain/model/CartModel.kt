package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.model.CartWithProductDto

data class CartModel(
    val id:Int,
    val userId:Int,
    var status:Int,
    var date:Long=0,
    val productList:MutableList<ProductCartModel> = mutableListOf()
)

fun CartWithProductDto.toCartModel() = CartModel(id=cart.id, userId = cart.userId,status=cart.status,date=cart.date, productList = productList.map {it.toProductCartModel()}.toMutableList())