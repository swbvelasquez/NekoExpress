package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.swbvelasquez.nekoexpress.domain.model.FavoriteProductModel

@Entity(
    tableName = "FavoriteProductTable",
    primaryKeys = ["userId","productId"],
    indices = [Index(name = "favorite_product_fk_idx",value = ["productId"])],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class FavoriteProductCrossRefEntity(
    @ColumnInfo(name="userId") val userId:Long,
    @ColumnInfo(name="productId") val productId:Long
)

fun FavoriteProductModel.toFavoriteProductEntity() = FavoriteProductCrossRefEntity(userId=userId,productId=productId)