package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swbvelasquez.nekoexpress.domain.model.CartModel

@Entity(tableName = "CartTable")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") val id:Int,
    @ColumnInfo(name="userId") val userId:Int
)

fun CartModel.toCartEntity() = CartEntity(id=id,userId=userId)