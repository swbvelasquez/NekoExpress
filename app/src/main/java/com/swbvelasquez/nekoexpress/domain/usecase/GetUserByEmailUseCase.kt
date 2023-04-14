package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.UserRepository
import com.swbvelasquez.nekoexpress.domain.model.UserModel

class GetUserByEmailUseCase {
    private val repository = UserRepository()

    suspend operator fun invoke(email:String):UserModel?{
        return repository.getUserByEmailFromDb(email)
    }
}