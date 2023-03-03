package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swbvelasquez.nekoexpress.domain.model.RatingModel

@Entity(tableName = "RatingTable")
data class RatingEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "productId") val productId:Long=-1,
    @ColumnInfo(name = "rate") val rate:Double,
    @ColumnInfo(name = "count") val count:Int
)

fun RatingModel.toRatingEntity(productId:Long) = RatingEntity(productId = productId, rate = rate, count = count)