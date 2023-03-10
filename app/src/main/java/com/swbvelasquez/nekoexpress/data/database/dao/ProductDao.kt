package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Constants
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
    suspend fun getProductWithRankingById(productId:Long):ProductWithRatingDto?

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
    suspend fun insertProductList(productList:List<ProductEntity>):List<Long>

    @Insert
    suspend fun insertRatingList(ratingList:List<RatingEntity>):List<Long>

    @Transaction
    suspend fun insertProductWithRatingList(productList:List<ProductEntity>, ratingList:List<RatingEntity>){
        val resultProductList = insertProductList(productList)
        val resultRatingList = insertRatingList(ratingList)

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
    suspend fun updateProductList(productList:List<ProductEntity>):Int

    @Update
    suspend fun updateRatingList(RatingList:List<RatingEntity>):Int

    @Transaction
    suspend fun updateProductWithRatingList(productList:List<ProductEntity>, ratingList:List<RatingEntity>){
        val resultProductList = updateProductList(productList)
        val resultRatingList = updateRatingList(ratingList)

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
    suspend fun deleteProductList(productList:List<ProductEntity>):Int

    @Delete
    suspend fun deleteRatingList(RatingList:List<RatingEntity>):Int

    @Transaction
    suspend fun deleteProductWithRatingList(productList:List<ProductEntity>, ratingList:List<RatingEntity>){
        val resultProductList = deleteProductList(productList)
        val resultRatingList = deleteRatingList(ratingList)

        if(resultProductList == Constants.ROOM_DB_OPERATION_FAIL || resultRatingList == Constants.ROOM_DB_OPERATION_FAIL){
            throw CustomException(CustomTypeException.DB_DELETE_LIST)
        }
    }

    @Query("delete from ProductTable where category = :category")
    suspend fun deleteAllProductsByCategory(category:String)

    @Query("delete from RatingTable where productId in (select productId from ProductTable where category = :category)")
    suspend fun deleteAllRatingByCategory(category:String)

    @Transaction
    suspend fun deleteAllProductsWithRatingByCategory(category: String){
        deleteAllRatingByCategory(category)
        deleteAllProductsByCategory(category)
    }
}