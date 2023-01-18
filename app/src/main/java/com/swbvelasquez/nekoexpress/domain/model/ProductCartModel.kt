package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.network.model.ProductCatalogDTO

data class ProductCartModel(
    val id:Int,
    val title:String,
    val price:Double,
    val category: String,
    val image:String,
    var quantity:Int=1,
    var total:Double=0.0
)

fun ProductCatalogModel.toProductCartModel() = ProductCartModel(id = id,title= title,price = price,category=category,image=image)