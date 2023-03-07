package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartCrossRefEntity
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
    val productCartList = productList.map { it.toProductCartModel() }
    var productCrossRef : ProductCartCrossRefEntity
    var subtotal=0.0

    for(product in productCartList){
        productCrossRef = productCrossRefList.first { x -> x.productId == product.productId }
        product.quantity = productCrossRef.quantity
        product.total = productCrossRef.total
        subtotal += product.total
    }

    val taxes = subtotal * Constants.TAXES_PERCENT
    val total = subtotal + taxes

    return CartModel(
        cartId = cart.cartId,
        userId = cart.userId,
        status = cart.status,
        date = cart.date,
        subtotal = subtotal,
        taxes = taxes,
        total = total,
        productList = productCartList.toMutableList()
    )
}
