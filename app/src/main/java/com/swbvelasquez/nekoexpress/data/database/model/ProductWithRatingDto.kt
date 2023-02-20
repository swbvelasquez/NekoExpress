package com.swbvelasquez.nekoexpress.data.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity
import com.swbvelasquez.nekoexpress.data.database.entity.RatingEntity

data class ProductWithRatingDto(
    @Embedded val productCatalog:ProductEntity, //Embedded permite incluir las propiedades como parte de la clase ProductCatalogWithRatingDto
    @Relation(
        parentColumn = "id", //entidad principal
        entityColumn = "productId", //entidad secundaria
        entity = RatingEntity::class //tipo de entidad retornada
    )
    val rating:RatingEntity
)


