package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.usecase.AddProductToCartUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetProductByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductCatalogDetailViewModel:ViewModel() {
    private val addProductToCartUseCase = AddProductToCartUseCase()
    private val getProductByIdUseCase = GetProductByIdUseCase()

    private val loading = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any?>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }
    private val productModel : MutableLiveData<ProductCatalogModel> by lazy {
        MutableLiveData<ProductCatalogModel>()
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getResult():LiveData<Any?> = result

    fun getProductDetails(productId:Long){
        viewModelScope.launch {
            loading.value = true

            try {
                val product = getProductByIdUseCase(productId)
                withContext(Dispatchers.IO){
                    delay(250)
                }
                productModel.value = product
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

    fun addProductToCart(product:ProductCartModel){
        viewModelScope.launch {
            loading.value = true

            try{
                addProductToCartUseCase(product)
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
}