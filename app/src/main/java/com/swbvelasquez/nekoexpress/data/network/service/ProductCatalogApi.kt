package com.swbvelasquez.nekoexpress.data.network.service

import com.swbvelasquez.nekoexpress.data.network.model.ProductCatalogDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductCatalogApi {
    @GET("products")
    suspend fun getAllProducts():Response<List<ProductCatalogDto>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id:Int):Response<ProductCatalogDto>
}