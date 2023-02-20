package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.*
import com.swbvelasquez.nekoexpress.data.database.entity.ProductEntity
import com.swbvelasquez.nekoexpress.data.database.model.ProductWithRatingDto

@Dao
interface ProductDao {
    @Transaction
    @Query("select * from ProductTable")
    suspend fun getAllProductsWithRanking():List<ProductWithRatingDto>?

    @Transaction
    @Query("select * from ProductTable where id = :id")
    suspend fun getProductWithRankingById(id:Int):ProductWithRatingDto?

    @Insert(onConflict = OnConflictStrategy.ABORT) //ABORT es el default, si falla hace rollback
    suspend fun insertProduct(product:ProductEntity):Long

    @Insert //Cuando falla un objeto, solo se hace rollback a ese objeto, el resto de la lista se inserta igual, si estuviera marcada con el tag @Transaction este insert, entonces la lista si se ejecuta en una misma transaccion
    suspend fun insertAllProducts(productList:List<ProductEntity>):List<Long>

    @Update
    suspend fun updateProduct(product:ProductEntity):Int

    @Update
    suspend fun updateAllProducts(productList:List<ProductEntity>):Int

    @Delete
    suspend fun deleteProduct(product:ProductEntity):Int

    @Delete
    suspend fun deleteAllProducts(productList:List<ProductEntity>):Int
}