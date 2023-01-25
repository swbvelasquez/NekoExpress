package com.swbvelasquez.nekoexpress

import android.app.Application
import androidx.room.Room
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.data.database.RoomDataBase

class NekoApplication:Application() {
    companion object{
        lateinit var database: RoomDataBase
    }

    override fun onCreate() {
        super.onCreate()

        try {
            database = Room
                .databaseBuilder(this, RoomDataBase::class.java, Constants.ROOM_DB_NAME)
                .build()
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }
}