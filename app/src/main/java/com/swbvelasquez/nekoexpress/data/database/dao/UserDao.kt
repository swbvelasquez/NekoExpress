package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.swbvelasquez.nekoexpress.data.database.entity.FavoriteProductCrossRefEntity
import com.swbvelasquez.nekoexpress.data.database.entity.UserEntity
import com.swbvelasquez.nekoexpress.data.database.model.UserWithFavoriteProductDto

@Dao
interface UserDao {
    @Transaction
    @Query("select * from UserTable where userId = :userId")
    suspend fun getAllFavoriteProductsByUserId(userId:Long):UserWithFavoriteProductDto?

    @Query("select * from UserTable where email = :email")
    suspend fun getUserByEmail(email:String):UserEntity?

    @Insert
    suspend fun insertUser(user:UserEntity):Long

    @Update
    suspend fun updateUser(user:UserEntity):Int

    @Insert
    suspend fun insertFavoriteProduct(product:FavoriteProductCrossRefEntity):Long

    @Delete
    suspend fun deleteFavoriteProduct(product:FavoriteProductCrossRefEntity):Int
}