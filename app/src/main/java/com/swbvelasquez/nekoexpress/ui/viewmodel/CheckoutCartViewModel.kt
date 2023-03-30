package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.domain.usecase.DeleteProductFromCartUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetCartByIdUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.ProceedToPayCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckoutCartViewModel(private val cartId:Long): ViewModel() {
    private val getCartByIdUseCase = GetCartByIdUseCase()
    private val deleteProductFromCartUseCase = DeleteProductFromCartUseCase()
    private val proceedToPayCartUseCase = ProceedToPayCartUseCase()
    private val loading = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any?>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
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

    fun deleteProductFromCart(product: ProductCartModel){
        viewModelScope.launch {
            loading.value = true

            try {
                deleteProductFromCartUseCase(product)

                result.value = product
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

                withContext(Dispatchers.IO){
                    delay(250)
                }

                result.value = true
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