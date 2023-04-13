package com.swbvelasquez.nekoexpress.domain.model

import androidx.room.ColumnInfo
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity
import com.swbvelasquez.nekoexpress.data.database.entity.UserEntity
import com.swbvelasquez.nekoexpress.data.database.model.UserWithFavoriteProductDto

data class UserModel(
    val userId:Long,
    val email:String,
    val phone:String,
    val firstName:String,
    val lastName:String,
    val registerDate:Long,
    val productFavoriteList:List<ProductEntity> = mutableListOf()
)

fun UserEntity.toUserModel() = UserModel(userId=userId,email=email,phone=phone,firstName=firstName,lastName=lastName,registerDate=registerDate)

fun UserWithFavoriteProductDto.toUserModel() = UserModel(userId=user.userId,email=user.email,phone=user.phone,firstName=user.firstName,lastName=user.lastName,registerDate=user.registerDate,productFavoriteList=productList)