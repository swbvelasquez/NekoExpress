package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.UserRepository
import com.swbvelasquez.nekoexpress.domain.model.FavoriteProductModel
import com.swbvelasquez.nekoexpress.domain.model.UserModel

class GetAllFavoriteProductsByUserIdUseCase {
    private val repository = UserRepository()

    suspend operator fun invoke(userId:Long):UserModel?{
        return repository.getAllFavoriteProductsByUserIdFromDb(userId)
    }
}