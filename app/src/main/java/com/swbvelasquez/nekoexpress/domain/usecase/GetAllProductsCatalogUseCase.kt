package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.ProductCatalogRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class GetAllProductsCatalogUseCase {
    private val repository = ProductCatalogRepository()

    suspend operator fun invoke():List<ProductCatalogModel>?{
        var productList = repository.getAllProductsFromApi()

        if(!productList.isNullOrEmpty()){
            repository.deleteAllProductsFromDb(productList)
            repository.insertAllProductsToDb(productList)
        }else{
            productList = repository.getAllProductsFromDatabase()
        }

        return productList
    }
}