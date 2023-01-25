package com.swbvelasquez.nekoexpress.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.swbvelasquez.nekoexpress.data.database.dao.ProductCatalogDao
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCatalogEntity
import com.swbvelasquez.nekoexpress.data.database.entity.RatingEntity

@Database(
    entities = [ProductCatalogEntity::class, RatingEntity::class],
    version = 1
)
abstract class RoomDataBase:RoomDatabase() {
    abstract fun getProductCatalogDao():ProductCatalogDao
}