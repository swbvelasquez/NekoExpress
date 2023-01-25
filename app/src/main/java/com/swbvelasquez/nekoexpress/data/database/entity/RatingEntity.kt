package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RatingTable")
data class RatingEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "productId") val productId:Int,
    @ColumnInfo(name = "rate") val rate:Double,
    @ColumnInfo(name = "count") val count:Int
)
