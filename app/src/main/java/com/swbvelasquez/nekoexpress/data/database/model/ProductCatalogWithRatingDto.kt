package com.swbvelasquez.nekoexpress.data.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCatalogEntity
import com.swbvelasquez.nekoexpress.data.database.entity.RatingEntity

data class ProductCatalogWithRatingDto(
    @Embedded val productCatalog:ProductCatalogEntity, //Embedded permite incluir las propiedades como parte de la clase ProductCatalogWithRatingDto
    @Relation(
        parentColumn = "id", //entidad principal
        entityColumn = "productId", //entidad secundaria
        entity = RatingEntity::class //tipo de entidad retornada
    )
    val rating:RatingEntity
)


