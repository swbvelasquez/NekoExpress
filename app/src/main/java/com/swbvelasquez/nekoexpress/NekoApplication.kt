package com.swbvelasquez.nekoexpress

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.data.database.RoomDataBase

class NekoApplication:Application() {
    companion object{
        lateinit var database: RoomDataBase
        lateinit var gson: Gson
    }

    override fun onCreate() {
        super.onCreate()

        try {
            database = RoomDataBase.getInstance(this)
            gson = GsonBuilder().create()
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }
}