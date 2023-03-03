package com.swbvelasquez.nekoexpress.data.network.model

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id")
    val productId:Long,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating: RatingDto
)


