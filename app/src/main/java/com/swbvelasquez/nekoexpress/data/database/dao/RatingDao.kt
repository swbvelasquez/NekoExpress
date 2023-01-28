package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.*
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCatalogEntity
import com.swbvelasquez.nekoexpress.data.database.entity.RatingEntity

@Dao
interface RatingDao {
    @Query("select * from RatingTable")
    suspend fun getAllRatings():List<RatingEntity>?

    @Query("select * from RatingTable where productId=:productId")
    suspend fun getRatingByProductId(productId:Int):RatingEntity?

    @Insert
    suspend fun insertRating(rating:RatingEntity):Long

    @Insert
    suspend fun insertAllRatings(ratingList:List<RatingEntity>)

    @Update
    suspend fun updateRating(rating:RatingEntity)

    @Update
    suspend fun updateAllRatings(RatingList:List<RatingEntity>)

    @Delete
    suspend fun deleteRating(rating:RatingEntity)

    @Delete
    suspend fun deleteAllRatings(RatingList:List<RatingEntity>)
}