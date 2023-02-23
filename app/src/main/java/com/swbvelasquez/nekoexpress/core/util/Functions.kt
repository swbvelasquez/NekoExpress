package com.swbvelasquez.nekoexpress.core.util

import android.content.Context
import android.widget.Toast
import com.google.gson.reflect.TypeToken
import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.data.network.interceptor.RetrofitInterceptor
import com.swbvelasquez.nekoexpress.data.network.model.CategoryDto
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


object Functions {
    fun getRetrofit(): Retrofit{
        val client = OkHttpClient
            .Builder()
            .addInterceptor(RetrofitInterceptor())
            .build()

        return Retrofit
            .Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun showSimpleMessage(context:Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    inline fun <reified T> T.toJson():String{
        return NekoApplication.gson.toJson(this)
    }

    inline fun <reified T> String.fromJson():T{
        val type: Type = object : TypeToken<T>() {}.type
        return NekoApplication.gson.fromJson(this,type)
    }

    fun getCompleteCategoryDto(index:Int,category: String):CategoryDto = CategoryDto(index+1,category, getCategoryImage(category))

    private fun getCategoryImage(category: String):String{
        return when(category){
            "electronics" -> "https://www.diariodenavarra.es/uploads/2020/09/18/60afe9cad4d7f.jpeg"
            "jewelery" -> "https://sc04.alicdn.com/kf/Hab658fd664144f94bd6fe213961490c81.jpg"
            "men's clothing" -> "https://i.pinimg.com/originals/29/9f/57/299f57da826f803a696fa2647457d10b.jpg"
            "women's clothing" -> "https://pm1.narvii.com/6339/0c1dc5efec4caf6b2957d5426f105b58b1f69a99_hq.jpg"
            else -> "https://popupsmart.com/blog/images/o/n/l/i/n/online-shopping-153a87f7.jpg"
        }
    }
}