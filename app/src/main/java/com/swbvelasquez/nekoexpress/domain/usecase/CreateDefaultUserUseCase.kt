package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.data.repository.UserRepository
import com.swbvelasquez.nekoexpress.domain.model.UserModel

class CreateDefaultUserUseCase {
    private val repository = UserRepository()

    suspend operator fun invoke(){
        var defaultUser = repository.getUserByEmailFromDb(Constants.DEFAULT_USER_EMAIL)

        if(defaultUser==null){
            defaultUser = UserModel(userId=0, email = "default.user@gmail.com", phone = "+51-945954578", firstName = "Default", lastName = "User", registerDate = Functions.getDateTimeNowInMs(), image = Constants.DEFAULT_USER_IMAGE)
            repository.insertUserToDb(defaultUser)
        }
    }
}