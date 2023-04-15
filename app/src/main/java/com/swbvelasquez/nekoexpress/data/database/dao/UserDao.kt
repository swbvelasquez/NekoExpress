package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.swbvelasquez.nekoexpress.data.database.entity.FavoriteProductCrossRefEntity
import com.swbvelasquez.nekoexpress.data.database.entity.UserEntity
import com.swbvelasquez.nekoexpress.data.database.model.UserFavoriteProductWithRatingDto
import com.swbvelasquez.nekoexpress.data.database.model.UserWithFavoriteProductDto

@Dao
interface UserDao {
    @Transaction
    @Query("select * from UserTable where userId = :userId") //Primera forma de como obtener una relacion 1 a muchos con room y definicion de modelo con @Relation
    suspend fun getUserWithFavoriteProductsByUserId(userId:Long):UserWithFavoriteProductDto?

    @Query("select p.*,r.count,r.rate from ProductTable p " +
            "inner join FavoriteProductTable f on p.productId = f.productId " +
            "inner join RatingTable r on p.productId = r.productId " +
            "where f.userId = :userId")
    fun getAllUserFavoriteProductsByUserId(userId:Long):LiveData<List<UserFavoriteProductWithRatingDto>>?

    @Query("select p.*,r.count,r.rate from ProductTable p " +
            "inner join FavoriteProductTable f on p.productId = f.productId " +
            "inner join RatingTable r on p.productId = r.productId " +
            "where f.userId = :userId and p.category = :category")
    suspend fun getAllFavoriteProductsByCategory(userId:Long, category:String):List<UserFavoriteProductWithRatingDto>?

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