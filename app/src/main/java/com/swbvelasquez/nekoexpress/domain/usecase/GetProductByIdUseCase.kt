package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.ProductRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class GetProductByIdUseCase {
    private val repository = ProductRepository()

    suspend operator fun invoke(productId:Long):ProductCatalogModel?{
        var product = repository.getProductByIdFromDb(productId)

        if(product==null){
            product = repository.getProductByIdFromApi(productId)

            if(product!=null) {
                repository.deleteProductFromDb(product)
                repository.insertProductToDb(product)
            }
        }

        return product
    }
}