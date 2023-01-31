package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.ProductCatalogRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class GetProductCatalogByIdUseCase {
    private val repository = ProductCatalogRepository()

    suspend operator fun invoke(id:Int):ProductCatalogModel?{
        var product = repository.getProductByIdFromApi(id)

        return product
    }
}