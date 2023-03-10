package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.ProductRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class GetProductsByCategoryUseCase {
    private val repository = ProductRepository()

    suspend operator fun invoke(category:String):List<ProductCatalogModel>?{
        var productList = repository.getProductsByCategoryFromApi(category)
        val productLocalList = repository.getAllProductsByCategoryFromDb(category)

        if(productList.isNullOrEmpty()){
            productList = productLocalList
        }else{
            val productInsertList:List<ProductCatalogModel> = if(productLocalList.isNullOrEmpty()){
                productList
            }else{
                productList.filterNot { productApi -> productApi.productId in productLocalList.map { productDb -> productDb.productId } }
            }

            if(productInsertList.isNotEmpty()) repository.insertProductListToDb(productInsertList)
        }

        return productList
    }
}