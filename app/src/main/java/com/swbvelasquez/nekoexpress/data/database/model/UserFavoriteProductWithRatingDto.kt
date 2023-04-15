package com.swbvelasquez.nekoexpress.data.database.model

data class UserFavoriteProductWithRatingDto(
    val productId:Long,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rate:Double,
    val count:Int)