package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity
import com.swbvelasquez.nekoexpress.data.database.model.ProductCartDto

data class ProductCartModel(
    val productId:Long,
    var cartId:Long=0,
    val title:String,
    val price:Double,
    val category: String,
    val image:String,
    var quantity:Int=1,
    var total:Double=0.0
)

fun ProductCatalogModel.toProductCartModel() = ProductCartModel(productId=productId,title=title,price=price,category=category,image=image)

fun ProductCartDto.toProductCartModel() = ProductCartModel(productId=product.productId,title=product.title,price=product.price,category=product.category,image=product.image,quantity=quantity,total=total)

fun ProductEntity.toProductCartModel() = ProductCartModel(productId=productId,title=title,price=price,category=category,image=image)