package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity
import com.swbvelasquez.nekoexpress.data.database.model.CartWithProductDto

data class CartModel(
    val cartId:Long=0,
    val userId:Long,
    var status:Int,
    var date:Long=0,
    var subtotal:Double=0.0,
    var taxes:Double=0.0,
    var total:Double=0.0,
    val productList:MutableList<ProductCartModel> = mutableListOf()
)

fun CartWithProductDto.toCartModel():CartModel{
    val productCartList:MutableList<ProductCartModel> = mutableListOf()
    var productCartModel : ProductCartModel
    var productEntity : ProductEntity

    for(productCartEntity in productCrossRefList){
        productEntity = productList.first { x -> x.productId == productCartEntity.productId }
        productCartModel = productCartEntity.toProductCartModel(productEntity.title,productEntity.category,productEntity.image)
        productCartList.add(productCartModel)
    }

    return CartModel(
        cartId = cart.cartId,
        userId = cart.userId,
        status = cart.status,
        date = cart.date,
        subtotal = cart.subtotal,
        taxes = cart.taxes,
        total = cart.total,
        productList = productCartList.toMutableList()
    )
}
