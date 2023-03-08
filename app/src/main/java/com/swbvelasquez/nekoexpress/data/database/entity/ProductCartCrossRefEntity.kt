package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.RESTRICT
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel

@Entity(
    tableName = "ProductCartTable",
    primaryKeys = ["cartId","productId","productCartId"],
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
    @ColumnInfo(name="productCartId") val productCartId:Long,
    @ColumnInfo(name="cartId") val cartId:Long,
    @ColumnInfo(name="productId") val productId:Long,
    val size:String,
    val color:String,
    val quantity:Int,
    val price:Double,
    val total:Double
)

fun ProductCartModel.toProductCartEntity() = ProductCartCrossRefEntity(productCartId=productCartId,cartId=cartId,productId=productId,size=size,color=color,quantity=quantity,price=price,total=total)