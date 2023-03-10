package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetLastAvailableCartUseCase
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val getLastAvailableCartUseCase = GetLastAvailableCartUseCase()
    private val loading = MutableLiveData<Boolean>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }
    private val cart : MutableLiveData<CartModel> by lazy {
        MutableLiveData<CartModel>()
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getCart(): LiveData<CartModel> = cart

    fun getLastAvailableCart(userId:Long){
        viewModelScope.launch {
            loading.value = true

            try{
                val result = getLastAvailableCartUseCase(userId)

                result?.let {
                    cart.value = it
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