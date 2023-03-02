package com.swbvelasquez.nekoexpress.data.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.swbvelasquez.nekoexpress.data.database.entity.CartEntity
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartEntityCrossRef

data class CartWithProductDto(
    @Embedded val cart: CartEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductCartEntityCrossRef::class)
    )
    val productList:List<ProductCartDto>
)
