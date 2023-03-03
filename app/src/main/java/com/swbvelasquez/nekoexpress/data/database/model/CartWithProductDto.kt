package com.swbvelasquez.nekoexpress.data.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.swbvelasquez.nekoexpress.data.database.entity.CartEntity
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartEntityCrossRef
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity

data class CartWithProductDto(
    @Embedded val cart: CartEntity,
    @Relation(
        parentColumn = "cartId",
        entityColumn = "productId",
        associateBy = Junction(ProductCartEntityCrossRef::class)
    )
    val productList:List<ProductEntity>
)
