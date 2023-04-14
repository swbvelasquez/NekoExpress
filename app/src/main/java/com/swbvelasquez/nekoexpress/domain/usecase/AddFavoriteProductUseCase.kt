package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.UserRepository
import com.swbvelasquez.nekoexpress.domain.model.FavoriteProductModel

class AddFavoriteProductUseCase {
    private val repository = UserRepository()

    suspend operator fun invoke(product:FavoriteProductModel){
        repository.insertFavoriteProductToDb(product)
    }
}