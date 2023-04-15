package com.swbvelasquez.nekoexpress.domain.usecase

import androidx.lifecycle.LiveData
import com.swbvelasquez.nekoexpress.data.database.model.UserFavoriteProductWithRatingDto
import com.swbvelasquez.nekoexpress.data.repository.UserRepository
import com.swbvelasquez.nekoexpress.domain.model.UserModel

class GetAllFavoriteProductsByUserIdUseCase {
    private val repository = UserRepository()

    operator fun invoke(userId:Long):LiveData<List<UserFavoriteProductWithRatingDto>>? {
        return repository.getAllFavoriteProductsByUserIdFromDb(userId)
    }
}