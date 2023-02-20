package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.ProductRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class GetProductByIdUseCase {
    private val repository = ProductRepository()

    suspend operator fun invoke(id:Int):ProductCatalogModel?{
        var product = repository.getProductByIdFromApi(id)

        if(product!=null){
            repository.deleteProductFromDb(product)
            repository.insertProductToDb(product)
        }else{
            product = repository.getProductByIdFromDb(id)
        }

        return product
    }
}