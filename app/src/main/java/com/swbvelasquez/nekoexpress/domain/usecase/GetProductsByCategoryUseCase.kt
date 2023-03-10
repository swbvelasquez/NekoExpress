package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.ProductRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class GetProductsByCategoryUseCase {
    private val repository = ProductRepository()

    suspend operator fun invoke(category:String):List<ProductCatalogModel>?{
        var productList = repository.getProductsByCategoryFromApi(category)

        if(productList.isNullOrEmpty()){
            productList = repository.getAllProductsByCategoryFromDb(category)
        }else{
            repository.deleteAllProductsByCategoryFromDb(category)
            repository.insertProductListToDb(productList)
        }

        return productList
    }
}