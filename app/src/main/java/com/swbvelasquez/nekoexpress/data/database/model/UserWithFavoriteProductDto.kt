package com.swbvelasquez.nekoexpress.data.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.swbvelasquez.nekoexpress.data.database.entity.FavoriteProductCrossRefEntity
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity
import com.swbvelasquez.nekoexpress.data.database.entity.UserEntity

data class UserWithFavoriteProductDto(
    @Embedded val user:UserEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "productId",
        associateBy = Junction(FavoriteProductCrossRefEntity::class)
    )
    val productList:List<ProductEntity>
)