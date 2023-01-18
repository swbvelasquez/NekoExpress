package com.swbvelasquez.nekoexpress.data.network.model

import com.swbvelasquez.nekoexpress.domain.model.RatingModel

data class ProductCatalogDTO(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating: RatingModel
)
