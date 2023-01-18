package com.swbvelasquez.nekoexpress.core.util

import com.swbvelasquez.nekoexpress.data.network.interceptor.RetrofitInterceptor
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
}