package com.swbvelasquez.nekoexpress.models

data class ProductModel(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating:RatingModel
)
