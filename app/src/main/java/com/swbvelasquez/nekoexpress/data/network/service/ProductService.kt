package com.swbvelasquez.nekoexpress.data.network.service

import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.data.network.model.ProductDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductService {
    private val retrofit = Functions.getRetrofit()
    private val service = retrofit.create(ProductApi::class.java)

    suspend fun getAllProducts():List<ProductDto>? {
        return withContext(Dispatchers.IO){
            service.getAllProducts().body()
        }
    }

    suspend fun getProductById(id:Int):ProductDto? {
        return withContext(Dispatchers.IO){
            service.getProductById(id).body()
        }
    }
}