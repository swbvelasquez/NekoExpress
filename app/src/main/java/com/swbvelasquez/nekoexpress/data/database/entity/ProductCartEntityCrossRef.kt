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
            parentColumns = ["id"],
            childColumns = ["cartId"],
            onDelete = RESTRICT
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = RESTRICT
        )
    ]
)
data class ProductCartEntityCrossRef(
    @ColumnInfo(name="cartId") val cartId:Int,
    @ColumnInfo(name="productId") val productId:Int,
    val quantity:Int,
    val total:Double
)

fun ProductCartModel.toProductCartEntity() = ProductCartEntityCrossRef(cartId=cartId,productId=id,quantity=quantity,total=total)