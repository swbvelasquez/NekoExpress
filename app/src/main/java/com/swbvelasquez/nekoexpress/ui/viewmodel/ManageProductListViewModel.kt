package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetAllProductsCatalogUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetProductCatalogByIdUseCase
import kotlinx.coroutines.launch

class ManageProductListViewModel:ViewModel() {
    private var getAllProductsCatalogUseCase = GetAllProductsCatalogUseCase()
    private var getProductCatalogByIdUseCase = GetProductCatalogByIdUseCase()
    private val loading = MutableLiveData<Boolean>()

    private val productCatalogList : MutableLiveData<MutableList<ProductCatalogModel>> by lazy {
        MutableLiveData<MutableList<ProductCatalogModel>>()
    }
    private val productCatalog : MutableLiveData<ProductCatalogModel> by lazy {
        MutableLiveData<ProductCatalogModel>()
    }
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }

    fun isLoading():LiveData<Boolean> = loading
    fun getTypeException():LiveData<CustomException> = customException
    fun getProductCatalogList():LiveData<MutableList<ProductCatalogModel>> = productCatalogList

    fun getAllProducts(){
        viewModelScope.launch {
            loading.value = true
            try {
                val result = getAllProductsCatalogUseCase()

                result?.let {
                    productCatalogList.value = result.toMutableList()
                }
            }catch (ex:CustomException) {
                customException.value = ex
            }catch (ex:Exception){
                customException.value = CustomException(CustomTypeException.UNKNOWN)
            }finally {
                loading.value = false
            }
        }
    }

    fun getProductById(id:Int){
        viewModelScope.launch {
            loading.value = true

            try {
                val result = getProductCatalogByIdUseCase(id)

                result?.let {
                    productCatalog.value = result
                }
            }catch (ex:CustomException) {
                customException.value = ex
            }catch (ex:Exception){
                customException.value = CustomException(CustomTypeException.UNKNOWN)
            }finally {
                loading.value = false
            }
        }
    }
}