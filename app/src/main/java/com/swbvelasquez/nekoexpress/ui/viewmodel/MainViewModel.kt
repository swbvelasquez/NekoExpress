package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.usecase.CreateDefaultUserUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetLastAvailableCartUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetTotalQuantityProductsByUserId
import kotlinx.coroutines.launch

class MainViewModel(private val userId:Long):ViewModel() {
    private val createDefaultUserUseCase = CreateDefaultUserUseCase()
    private val getLastAvailableCartUseCase = GetLastAvailableCartUseCase()
    private val getTotalQuantityProductsByUserId = GetTotalQuantityProductsByUserId()
    private val loading = MutableLiveData<Boolean>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }
    private val cartId : MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }
    val totalQuantity = liveData {
        emitSource(getTotalQuantityProductsByUserId(userId))
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getCartId(): LiveData<Long> = cartId

    init {
        viewModelScope.launch {
            viewModelScope.launch {
                loading.value = true

                try{
                    createDefaultUserUseCase()
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