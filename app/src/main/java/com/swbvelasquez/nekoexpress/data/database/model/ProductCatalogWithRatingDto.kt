package com.swbvelasquez.nekoexpress.data.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCatalogEntity
import com.swbvelasquez.nekoexpress.data.database.entity.RatingEntity

data class ProductCatalogWithRatingDto(
    @Embedded val productCatalog:ProductCatalogEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId",
        entity = RatingEntity::class
    )
    val rating:RatingEntity
)


