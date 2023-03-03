package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swbvelasquez.nekoexpress.domain.model.CartModel

@Entity(tableName = "CartTable")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="cartId") val cartId:Long,
    @ColumnInfo(name="userId") val userId:Long,
    @ColumnInfo(name="status") var status:Int,
    @ColumnInfo(name="date") var date:Long
)

fun CartModel.toCartEntity() = CartEntity(cartId=cartId,userId=userId,status=status,date=date)