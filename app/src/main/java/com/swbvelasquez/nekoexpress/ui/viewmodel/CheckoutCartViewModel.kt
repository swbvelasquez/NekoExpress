package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetCartByIdUseCase
import kotlinx.coroutines.launch

class CheckoutCartViewModel(private val cartId:Long): ViewModel() {
    private val getCartByIdUseCase = GetCartByIdUseCase()
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

    init {
        viewModelScope.launch {
            loading.value = true

            try{
                val result = getCartByIdUseCase(cartId)

                result?.let {
                    cart.value = result
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
class CheckoutCartViewModelFactory(private val cartId:Long) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CheckoutCartViewModel(cartId) as T
    }
}