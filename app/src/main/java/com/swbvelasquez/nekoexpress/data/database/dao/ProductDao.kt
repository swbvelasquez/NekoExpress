package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity
import com.swbvelasquez.nekoexpress.data.database.entity.RatingEntity
import com.swbvelasquez.nekoexpress.data.database.model.ProductWithRatingDto

@Dao
interface ProductDao {
    @Transaction
    @Query("select * from ProductTable")
    suspend fun getAllProductsWithRanking():List<ProductWithRatingDto>?

    @Transaction
    @Query("select * from ProductTable where productId = :productId")
    suspend fun getProductWithRankingById(productId:Int):ProductWithRatingDto?

    @Transaction
    @Query("select * from ProductTable where category = :category")
    suspend fun getProductsWithRankingByCategory(category:String):List<ProductWithRatingDto>?

    @Insert(onConflict = OnConflictStrategy.ABORT) //ABORT es el default, si falla hace rollback
    suspend fun insertProduct(product:ProductEntity):Long

    @Insert //si es un insert retorna los ids si es auto generado y retorna 1 si es generado manualmente, cuando falla retorna -1
    suspend fun insertRating(rating: RatingEntity):Long

    @Transaction
    suspend fun insertProductWithRating(product:ProductEntity, rating:RatingEntity){
        val resultProduct = insertProduct(product)
        val resultRating = insertRating(rating)

        if(resultProduct <= 0 || resultRating <= 0){
            throw CustomException(CustomTypeException.DB_INSERT_ONE)
        }
    }

    @Insert //Cuando falla un objeto, solo se hace rollback a ese objeto, el resto de la lista se inserta igual, si estuviera marcada con el tag @Transaction este insert, entonces la lista si se ejecuta en una misma transaccion
    suspend fun insertAllProducts(productList:List<ProductEntity>):List<Long>

    @Insert
    suspend fun insertAllRatings(ratingList:List<RatingEntity>):List<Long>

    @Transaction
    suspend fun insertAllProductsWithRating(productList:List<ProductEntity>, ratingList:List<RatingEntity>){
        val resultProductList = insertAllProducts(productList)
        val resultRatingList = insertAllRatings(ratingList)

        if(resultProductList.isEmpty() || resultProductList.contains(-1) || resultRatingList.isEmpty() || resultRatingList.contains(-1)){
            throw CustomException(CustomTypeException.DB_INSERT_LIST)
        }
    }

    @Update //si es un update o delete, retorna la cantidad de filas afectadas o -1 si fallo
    suspend fun updateProduct(product:ProductEntity):Int

    @Update
    suspend fun updateRating(rating:RatingEntity):Int

    @Transaction
    suspend fun updateProductWithRating(product:ProductEntity, rating:RatingEntity){
        val resultProduct = updateProduct(product)
        val resultRating = updateRating(rating)

        if(resultProduct <= 0 || resultRating <= 0){
            throw CustomException(CustomTypeException.DB_UPDATE_ONE)
        }
    }

    @Update
    suspend fun updateAllProducts(productList:List<ProductEntity>):Int

    @Update
    suspend fun updateAllRatings(RatingList:List<RatingEntity>):Int

    @Transaction
    suspend fun updateAllProductsWithRating(productList:List<ProductEntity>, ratingList:List<RatingEntity>){
        val resultProductList = updateAllProducts(productList)
        val resultRatingList = updateAllRatings(ratingList)

        if(resultProductList != productList.size || resultRatingList != ratingList.size){
            throw CustomException(CustomTypeException.DB_UPDATE_LIST)
        }
    }

    @Delete
    suspend fun deleteProduct(product:ProductEntity):Int

    @Delete
    suspend fun deleteRating(rating:RatingEntity):Int

    @Transaction
    suspend fun deleteProductWithRating(product:ProductEntity, rating:RatingEntity){
        val resultProduct = deleteProduct(product)
        val resultRating = deleteRating(rating)

        if(resultProduct <= 0 || resultRating <= 0){
            throw CustomException(CustomTypeException.DB_DELETE_ONE)
        }
    }

    @Delete
    suspend fun deleteAllProducts(productList:List<ProductEntity>):Int

    @Delete
    suspend fun deleteAllRatings(RatingList:List<RatingEntity>):Int

    @Transaction
    suspend fun deleteAllProductsWithRating(productList:List<ProductEntity>, ratingList:List<RatingEntity>){
        val resultProductList = deleteAllProducts(productList)
        val resultRatingList = deleteAllRatings(ratingList)

        if(resultProductList != productList.size || resultRatingList != ratingList.size){
            throw CustomException(CustomTypeException.DB_DELETE_LIST)
        }
    }
}