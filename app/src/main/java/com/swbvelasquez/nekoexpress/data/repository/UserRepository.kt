package com.swbvelasquez.nekoexpress.data.repository

import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.data.database.entity.*
import com.swbvelasquez.nekoexpress.domain.model.FavoriteProductModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.model.UserModel
import com.swbvelasquez.nekoexpress.domain.model.toUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    private val userDao = NekoApplication.database.getUserDao()

    suspend fun getAllFavoriteProductsByUserId(userId:Long): UserModel?{
        val userEntity = withContext(Dispatchers.IO){ userDao.getAllFavoriteProductsByUserId(userId) }
        var userModel : UserModel? = null

        if(userEntity!=null){
            userModel = userEntity.toUserModel()
        }

        return userModel
    }

    suspend fun getUserByEmail(email:String): UserModel?{
        val userEntity = withContext(Dispatchers.IO){ userDao.getUserByEmail(email) }
        var userModel : UserModel? = null

        if(userEntity!=null){
            userModel = userEntity.toUserModel()
        }

        return userModel
    }

    suspend fun insertFavoriteProduct(product: FavoriteProductModel){
        val productEntity = product.toFavoriteProductEntity()

        val result = withContext(Dispatchers.IO){
            userDao.insertFavoriteProduct(productEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_ONE)
    }

    suspend fun deleteFavoriteProduct(product:FavoriteProductModel){
        val productEntity = product.toFavoriteProductEntity()

        val result = withContext(Dispatchers.IO){
            userDao.insertFavoriteProduct(productEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_ONE)
    }
}