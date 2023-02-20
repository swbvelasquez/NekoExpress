package com.swbvelasquez.nekoexpress.data.network.service

import com.swbvelasquez.nekoexpress.data.network.model.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getAllProducts():Response<List<ProductDto>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id:Int):Response<ProductDto>
}