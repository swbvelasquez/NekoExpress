package com.swbvelasquez.nekoexpress.data.database.model

import androidx.room.Embedded
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity

data class ProductCartDto(
    @Embedded val product:ProductEntity,
    val quantity:Int,
    val total:Double
)
