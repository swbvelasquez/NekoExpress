package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.ProductRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class GetAllProductsUseCase {
    private val repository = ProductRepository()

    suspend operator fun invoke():List<ProductCatalogModel>?{
        var productList = repository.getAllProductsFromApi()

        if(!productList.isNullOrEmpty()){
            repository.deleteAllProductsFromDb(productList)
            repository.insertAllProductsToDb(productList)
        }else{
            productList = repository.getAllProductsFromDb()
        }

        return productList
    }
}