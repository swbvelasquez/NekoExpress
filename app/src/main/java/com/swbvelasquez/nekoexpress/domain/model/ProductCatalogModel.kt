package com.swbvelasquez.nekoexpress.domain.model

data class ProductCatalogModel(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating: RatingModel
)

