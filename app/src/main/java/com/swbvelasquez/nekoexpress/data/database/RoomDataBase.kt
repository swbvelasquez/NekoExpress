package com.swbvelasquez.nekoexpress.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.swbvelasquez.nekoexpress.data.database.dao.CartDao
import com.swbvelasquez.nekoexpress.data.database.dao.ProductDao
import com.swbvelasquez.nekoexpress.data.database.dao.CategoryDao
import com.swbvelasquez.nekoexpress.data.database.dao.InvoiceDao
import com.swbvelasquez.nekoexpress.data.database.entity.*

@Database(
    entities = [
        ProductEntity::class,
        RatingEntity::class,
        CategoryEntity::class,
        CartEntity::class,
        ProductCartCrossRefEntity::class,
        InvoiceEntity::class,
        InvoiceDetailEntity::class],
    version = 5
)
abstract class RoomDataBase:RoomDatabase() {
    abstract fun getProductDao() : ProductDao
    abstract fun getCategoryDao() : CategoryDao
    abstract fun getCartDao() : CartDao
    abstract fun getInvoiceDao() : InvoiceDao
}