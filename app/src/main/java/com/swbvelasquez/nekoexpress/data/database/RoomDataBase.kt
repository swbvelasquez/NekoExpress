package com.swbvelasquez.nekoexpress.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.data.database.dao.*
import com.swbvelasquez.nekoexpress.data.database.entity.*

@Database(
    entities = [
        ProductEntity::class,
        RatingEntity::class,
        CategoryEntity::class,
        CartEntity::class,
        ProductCartCrossRefEntity::class,
        InvoiceEntity::class,
        InvoiceDetailEntity::class,
        UserEntity::class,
        FavoriteProductCrossRefEntity::class],
    version = 1
)
abstract class RoomDataBase:RoomDatabase() {
    abstract fun getProductDao() : ProductDao
    abstract fun getCategoryDao() : CategoryDao
    abstract fun getCartDao() : CartDao
    abstract fun getInvoiceDao() : InvoiceDao
    abstract fun getUserDao() : UserDao

    companion object {
        private var INSTANCE: RoomDataBase? = null

        fun getInstance(context: Context): RoomDataBase{
            return INSTANCE?: synchronized(this){ // El sincronized se asegura que solo un hilo pueda ejecutar este codigo a la vez, el resto espera en fila
                val instance = Room
                    .databaseBuilder(context.applicationContext //Se utiliza el contexto de la aplicacion para evitar memory leaks
                        ,RoomDataBase::class.java
                        ,Constants.ROOM_DB_NAME)
                    .fallbackToDestructiveMigration() //permite destruir los esquemas anteriores, solo usar en desarrollo, jamas produccion
                    .build()

                INSTANCE = instance
                instance
            }

        }
    }
}