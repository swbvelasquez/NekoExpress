package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.model.DeliveryAddressModel
import com.swbvelasquez.nekoexpress.domain.usecase.ConfirmOrderUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetCartByIdUseCase
import kotlinx.coroutines.launch

class PaymentDetailViewModel(private val cartId:Long):ViewModel() {
    private val getCartByIdUseCase = GetCartByIdUseCase()
    private val confirmOrderUseCase = ConfirmOrderUseCase()
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
    fun getResult(): LiveData<Any?> = result

    init {
        viewModelScope.launch {
            loading.value = true

            try{
                val cartResult = getCartByIdUseCase(cartId)

                cartResult?.let {
                    cart.value = cartResult
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

    fun confirmOrder(deliveryAddress: DeliveryAddressModel){
        viewModelScope.launch {
            loading.value = true
            var operationSuccess = false

            try {
                cart.value?.let {
                    confirmOrderUseCase(it,deliveryAddress)
                    operationSuccess = true
                }
            }catch (ex:CustomException){
                customException.value = ex
            }catch (ex:Exception){
                customException.value = CustomException(CustomTypeException.UNKNOWN)
            }finally {
                loading.value = false
                result.value = operationSuccess
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PaymentDetailViewModelFactory(private val cartId:Long) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PaymentDetailViewModel(cartId) as T
    }
}