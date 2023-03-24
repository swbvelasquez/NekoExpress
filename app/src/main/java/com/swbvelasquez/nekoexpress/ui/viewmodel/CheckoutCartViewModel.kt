package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetCartByIdUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.ProceedToPayCartUseCase
import kotlinx.coroutines.launch

class CheckoutCartViewModel(private val cartId:Long): ViewModel() {
    private val getCartByIdUseCase = GetCartByIdUseCase()
    private val proceedToPayCartUseCase = ProceedToPayCartUseCase()
    private val loading = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any?>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }
    private val cart : MutableLiveData<CartModel> by lazy {
        MutableLiveData<CartModel>()
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getResult():LiveData<Any?> = result

    init {
        viewModelScope.launch {
            loading.value = true

            try{
                val cartResult = getCartByIdUseCase(cartId)

                cartResult?.let {
                    cart.value = cartResult
                    result.value = cartResult
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

    fun proceedToPayCart(cartModel:CartModel){
        viewModelScope.launch {
            loading.value = true

            try {
                proceedToPayCartUseCase(cartModel)

                cart.value = cartModel
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