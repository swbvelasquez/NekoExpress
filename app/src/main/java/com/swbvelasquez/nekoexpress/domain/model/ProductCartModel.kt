package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartCrossRefEntity
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity
import com.swbvelasquez.nekoexpress.data.database.model.ProductCartDto

data class ProductCartModel(
    val productCartId:Long,
    val productId:Long,
    val cartId:Long,
    val title:String,
    val price:Double,
    val category: String,
    val image:String,
    var quantity:Int=1,
    var total:Double=0.0,
    var size:String="",
    var color:String=""
)

fun ProductCatalogModel.toProductCartModel(productCartId:Long,cartId:Long) = ProductCartModel(productCartId=productCartId,productId=productId,cartId=cartId,title=title,price=price,category=category,image=image,size=size,color=color)

fun ProductCartCrossRefEntity.toProductCartModel(title:String,category:String,image:String) = ProductCartModel(productCartId=productCartId,productId=productId,cartId=cartId,title=title
    ,price=price,category=category,image=image,quantity=quantity,total=total,size=size,color=color)