package com.swbvelasquez.nekoexpress.data.network.service

import com.swbvelasquez.nekoexpress.core.util.Functions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryService {
    private val retrofit = Functions.getRetrofit()
    private val service = retrofit.create(CategoryApi::class.java)

    suspend fun getAllCategories():List<String>? = withContext(Dispatchers.IO){
        service.getAllCategories().body()
    }
}