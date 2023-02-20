package com.swbvelasquez.nekoexpress.data.network.model

data class ProductDto(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating: RatingDto
)


