package com.swbvelasquez.nekoexpress.data.network.service

import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.data.network.model.ProductCatalogDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductCatalogService {
    private val retrofit = Functions.getRetrofit()
    private val service = retrofit.create(ProductCatalogApi::class.java)

    suspend fun getAllProducts():List<ProductCatalogDTO>? {
        return withContext(Dispatchers.IO){
            service.getAllProducts().body()
        }
    }

    suspend fun getProductById(id:Int):ProductCatalogDTO? {
        return withContext(Dispatchers.IO){
            service.getProductById(id).body()
        }
    }
}