package com.swbvelasquez.nekoexpress.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
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
    version = 2
)
abstract class RoomDataBase:RoomDatabase() {
    abstract fun getProductDao() : ProductDao
    abstract fun getCategoryDao() : CategoryDao
    abstract fun getCartDao() : CartDao
    abstract fun getInvoiceDao() : InvoiceDao
    abstract fun getUserDao() : UserDao

    companion object {
        private var INSTANCE: RoomDataBase? = null

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Agregar la nueva columna a la tabla UserTable
                database.execSQL("alter table UserTable add column image text not null default '${Constants.DEFAULT_USER_IMAGE}'")
            }
        }

        fun getInstance(context: Context): RoomDataBase{
            return INSTANCE?: synchronized(this){ // El sincronized se asegura que solo un hilo pueda ejecutar este codigo a la vez, el resto espera en fila
                val instance = Room
                    .databaseBuilder(context.applicationContext //Se utiliza el contexto de la aplicacion para evitar memory leaks
                        ,RoomDataBase::class.java
                        ,Constants.ROOM_DB_NAME)
                    //.fallbackToDestructiveMigration() //permite destruir los esquemas anteriores, solo usar en desarrollo, jamas produccion
                    .addMigrations(MIGRATION_1_2)
                    .build()

                INSTANCE = instance
                instance
            }

        }
    }
}