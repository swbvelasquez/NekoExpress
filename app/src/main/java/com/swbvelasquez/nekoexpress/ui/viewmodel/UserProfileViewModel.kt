package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.model.UserModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetUserByEmailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileViewModel(private val email:String):ViewModel() {
    private val getUserByEmailUseCase = GetUserByEmailUseCase()
    private val loading = MutableLiveData<Boolean>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }
    private val user : MutableLiveData<UserModel> by lazy {
        MutableLiveData<UserModel>()
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getUser(): LiveData<UserModel> = user

    init {
        viewModelScope.launch {
            loading.value = true

            try{
                val result = getUserByEmailUseCase(email)

                withContext(Dispatchers.IO){
                    delay(250)
                }

                result?.let {
                    user.value = result
                }
            }catch (ex:CustomException){
                customException.value = ex
            }catch (ex:Exception){
                customException.value = CustomException(CustomTypeException.UNKNOWN)
            }finally {
                loading.value = false
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class UserProfileViewModelFactory(private val email:String) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(email) as T
    }
}