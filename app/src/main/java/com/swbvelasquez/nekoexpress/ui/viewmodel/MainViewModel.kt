package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetLastAvailableCartUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetTotalQuantityProductsByIdCart
import kotlinx.coroutines.launch

class MainViewModel(private val userId:Long):ViewModel() {
    private val getLastAvailableCartUseCase = GetLastAvailableCartUseCase()
    private val getTotalQuantityProductsByIdCart = GetTotalQuantityProductsByIdCart()
    private val loading = MutableLiveData<Boolean>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }
    private val cartId : MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }
    val totalQuantity = liveData {
        emitSource(getTotalQuantityProductsByIdCart(userId))
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getCartId(): LiveData<Long> = cartId

    fun getLastAvailableCart(){
        viewModelScope.launch {
            loading.value = true

            try{
                val result = getLastAvailableCartUseCase(userId)

                result?.let {
                    cartId.value = it.cartId
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
class MainViewModelFactory(private val userId:Long) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(userId) as T
    }
}