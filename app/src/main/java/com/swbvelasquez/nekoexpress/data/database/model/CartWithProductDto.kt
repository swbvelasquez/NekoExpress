package com.swbvelasquez.nekoexpress.data.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.swbvelasquez.nekoexpress.data.database.entity.CartEntity
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartCrossRefEntity
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity

data class CartWithProductDto(
    @Embedded val cart: CartEntity,
    @Relation(
        parentColumn = "cartId",
        entityColumn = "productId",
        associateBy = Junction(ProductCartCrossRefEntity::class)
    )
    val productList:List<ProductEntity>,
    @Relation(
        parentColumn = "cartId",
        entityColumn = "cartId",
        entity = ProductCartCrossRefEntity::class
    )
    val productCrossRefList:List<ProductCartCrossRefEntity>
)
