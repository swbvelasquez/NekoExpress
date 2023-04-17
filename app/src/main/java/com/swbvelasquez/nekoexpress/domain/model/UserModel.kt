package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.UserEntity
import com.swbvelasquez.nekoexpress.data.database.model.UserWithFavoriteProductDto

data class UserModel(
    val userId:Long,
    val email:String,
    val phone:String,
    val firstName:String,
    val lastName:String,
    val image:String,
    val registerDate:Long,
    val favoriteProductList:MutableList<ProductCatalogModel> = mutableListOf()
)

fun UserEntity.toUserModel() = UserModel(userId=userId,email=email,phone=phone,firstName=firstName,lastName=lastName,image=image,registerDate=registerDate)

fun UserWithFavoriteProductDto.toUserModel() = UserModel(userId=user.userId,email=user.email,phone=user.phone,firstName=user.firstName
    ,lastName=user.lastName,image=user.image,registerDate=user.registerDate,favoriteProductList=productList.map { it.toProductCatalogModel(true) }.toMutableList())