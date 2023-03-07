package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetLastAvailableCartUseCase
import kotlinx.coroutines.launch

class DetailProductCatalogViewModel:ViewModel() {
    private val getLastAvailableCartUseCase = GetLastAvailableCartUseCase()

    private val loading = MutableLiveData<Boolean>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }
    private val productModel : MutableLiveData<ProductCatalogModel> by lazy {
        MutableLiveData<ProductCatalogModel>()
    }
    private val cartModel : MutableLiveData<CartModel> by lazy {
        MutableLiveData<CartModel>()
    }
    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getProduct():LiveData<ProductCatalogModel> = productModel
    fun getCart():LiveData<CartModel> = cartModel

    fun setInitValues(product:ProductCatalogModel, cart:CartModel){
        viewModelScope.launch {
            loading.value = true
            productModel.value = product
            cartModel.value = cart
            loading.value = false
        }
    }
}