package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.swbvelasquez.nekoexpress.data.database.model.ProductCatalogWithRatingDto

@Dao
interface ProductCatalogDao {
    @Transaction
    @Query("select * from ProductCatalogTable")
    suspend fun getAllProductsWithRanking():List<ProductCatalogWithRatingDto>?
}