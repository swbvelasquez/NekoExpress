package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetAllCategoriesUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetProductsByCategoryUseCase
import kotlinx.coroutines.launch

class ExposeProductCatalogViewModel:ViewModel() {
    private val getProductsByCategoryUseCase = GetProductsByCategoryUseCase()
    private val loading = MutableLiveData<Boolean>()

    private val productList: MutableLiveData<MutableList<ProductCatalogModel>> by lazy {
        MutableLiveData<MutableList<ProductCatalogModel>>()
    }
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getProductList():LiveData<MutableList<ProductCatalogModel>> = productList

    fun getProductsByCategory(category:String){
        viewModelScope.launch {
            loading.value = true

            try{
                val result = getProductsByCategoryUseCase(category)

                result?.let {
                    productList.value = it.toMutableList()
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