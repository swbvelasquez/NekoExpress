package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.network.model.ProductCatalogDTO

data class ProductCatalogModel(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating: RatingModel
)

fun ProductCatalogDTO.toProductCatalogModel() = ProductCatalogModel(id=id,title=title,price=price,description=description,category=category,image=image,rating=rating)