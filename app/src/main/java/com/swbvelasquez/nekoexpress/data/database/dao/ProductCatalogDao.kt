package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.*
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCatalogEntity
import com.swbvelasquez.nekoexpress.data.database.model.ProductCatalogWithRatingDto

@Dao
interface ProductCatalogDao {
    @Transaction
    @Query("select * from ProductCatalogTable")
    suspend fun getAllProductsWithRanking():List<ProductCatalogWithRatingDto>?

    @Transaction
    @Query("select * from ProductCatalogTable where id = :id")
    suspend fun getProductWithRankingById(id:Int):ProductCatalogWithRatingDto?

    @Insert(onConflict = OnConflictStrategy.ABORT) //ABORT es el default, si falla hace rollback
    suspend fun insertProduct(product:ProductCatalogEntity):Long

    @Insert //Cuando falla un objeto, solo se hace rollback a ese objeto, el resto de la lista se inserta igual, si estuviera marcada con el tag @Transaction este insert, entonces la lista si se ejecuta en una misma transaccion
    suspend fun insertAllProducts(productList:List<ProductCatalogEntity>):List<Long>

    @Update
    suspend fun updateProduct(product:ProductCatalogEntity):Int

    @Update
    suspend fun updateAllProducts(productList:List<ProductCatalogEntity>):Int

    @Delete
    suspend fun deleteProduct(product:ProductCatalogEntity):Int

    @Delete
    suspend fun deleteAllProducts(productList:List<ProductCatalogEntity>):Int
}