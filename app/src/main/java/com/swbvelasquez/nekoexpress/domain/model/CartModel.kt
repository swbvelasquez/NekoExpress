package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartCrossRefEntity
import com.swbvelasquez.nekoexpress.data.database.model.CartWithProductDto

data class CartModel(
    val cartId:Long,
    val userId:Long,
    var status:Int,
    var date:Long=0,
    val productList:MutableList<ProductCartModel> = mutableListOf()
)

fun CartWithProductDto.toCartModel():CartModel{
    val productCartList = productList.map { it.toProductCartModel() }
    var productCrossRef : ProductCartCrossRefEntity

    for(product in productCartList){
        productCrossRef = productCrossRefList.first { x -> x.productId == product.productId }
        product.quantity = productCrossRef.quantity
        product.total = productCrossRef.total
    }

    return CartModel(
        cartId = cart.cartId,
        userId = cart.userId,
        status = cart.status,
        date = cart.date,
        productList = productCartList.toMutableList()
    )
}
