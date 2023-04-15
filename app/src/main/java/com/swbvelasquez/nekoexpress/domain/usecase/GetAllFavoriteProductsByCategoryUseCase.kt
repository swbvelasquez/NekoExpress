package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.UserRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class GetAllFavoriteProductsByCategoryUseCase {
    private val repository = UserRepository()

    suspend operator fun invoke(userId:Long, category:String):List<ProductCatalogModel>? {
        return repository.getAllFavoriteProductsByCategoryFromDb(userId,category)
    }
}