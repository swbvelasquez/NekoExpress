package com.swbvelasquez.nekoexpress.data.network.model

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    val categoryId:Long,
    val name:String,
    val image:String
)
