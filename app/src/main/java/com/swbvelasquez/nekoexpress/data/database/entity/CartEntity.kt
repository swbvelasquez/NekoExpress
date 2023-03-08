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
    @ColumnInfo(name="status") val status:Int,
    @ColumnInfo(name="date") val date:Long,
    @ColumnInfo(name="subtotal") val subtotal:Double,
    @ColumnInfo(name="taxes") val taxes:Double,
    @ColumnInfo(name="total") val total:Double
)

fun CartModel.toCartEntity() = CartEntity(cartId=cartId,userId=userId,status=status,date=date,subtotal=subtotal,taxes=taxes,total=total)