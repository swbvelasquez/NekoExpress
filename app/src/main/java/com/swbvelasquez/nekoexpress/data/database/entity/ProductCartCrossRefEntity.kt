package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.RESTRICT
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel

@Entity(
    tableName = "ProductCartTable",
    primaryKeys = ["cartId","productId"],
    foreignKeys = [
        ForeignKey(
            entity = CartEntity::class,
            parentColumns = ["cartId"],
            childColumns = ["cartId"],
            onDelete = RESTRICT
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = RESTRICT
        )
    ]
)
data class ProductCartCrossRefEntity(
    @ColumnInfo(name="cartId") val cartId:Long,
    @ColumnInfo(name="productId") val productId:Long,
    val quantity:Int,
    val total:Double
)

fun ProductCartModel.toProductCartEntity() = ProductCartCrossRefEntity(cartId=cartId,productId=productId,quantity=quantity,total=total)