package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartEntityCrossRef
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity
import com.swbvelasquez.nekoexpress.data.database.model.ProductCartDto

data class ProductCartModel(
    val id:Int,
    var cartId:Int=0,
    val title:String,
    val price:Double,
    val category: String,
    val image:String,
    var quantity:Int=1,
    var total:Double=0.0
)

fun ProductCatalogModel.toProductCartModel() = ProductCartModel(id=id,title=title,price=price,category=category,image=image)

fun ProductCartDto.toProductCartModel() = ProductCartModel(id=product.id,title=product.title,price=product.price,category=product.category,image=product.image,quantity=quantity,total=total)
