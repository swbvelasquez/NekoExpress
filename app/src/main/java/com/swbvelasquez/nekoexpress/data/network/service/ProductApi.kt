package com.swbvelasquez.nekoexpress.data.network.service

import com.swbvelasquez.nekoexpress.data.network.model.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getAllProducts():Response<List<ProductDto>>

    @GET("products/{productId}")
    suspend fun getProductById(@Path("productId") productId:Long):Response<ProductDto>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category:String):Response<List<ProductDto>>
}