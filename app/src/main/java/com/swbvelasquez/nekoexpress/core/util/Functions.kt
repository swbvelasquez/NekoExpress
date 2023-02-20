package com.swbvelasquez.nekoexpress.core.util

import android.content.Context
import android.widget.Toast
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.data.network.interceptor.RetrofitInterceptor
import com.swbvelasquez.nekoexpress.data.network.model.CategoryDto
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    fun getCompleteCategoryDto(index:Int,category: String):CategoryDto = CategoryDto(index+1,category, getCategoryImage(category))

    private fun getCategoryImage(category: String):Int{
        return when(category){
            "electronics" -> R.drawable.category_electronics_thumbnail
            "jewelery" -> R.drawable.category_jewelery_thumbnail
            "men's clothing" -> R.drawable.category_men_thumbnail
            "women's clothing" -> R.drawable.category_women_thumbnail
            else -> R.drawable.category_electronics_thumbnail
        }
    }
}