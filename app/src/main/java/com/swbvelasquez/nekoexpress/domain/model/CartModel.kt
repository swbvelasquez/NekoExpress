package com.swbvelasquez.nekoexpress.domain.model

data class CartModel(
    val cartId:Long,
    val userId:Long,
    var status:Int,
    var date:Long=0,
    val productList:MutableList<ProductCartModel> = mutableListOf()
)
/*
fun CartWithProductDto.toCartModel() = CartModel(cartId=cart.cartId, userId = cart.userId,status=cart.status,date=cart.date, productList = productList.map {it.toProductCartModel()}.toMutableList())

 */