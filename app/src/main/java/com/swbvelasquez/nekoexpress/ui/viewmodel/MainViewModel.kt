package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.data.repository.ProductCatalogRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val repository = ProductCatalogRepository()
    private val loading = MutableLiveData<Boolean>()

    private val productCatalogList : MutableLiveData<MutableList<ProductCatalogModel>> by lazy {
        MutableLiveData<MutableList<ProductCatalogModel>>()
    }
    private val productCatalog : MutableLiveData<ProductCatalogModel> by lazy {
        MutableLiveData<ProductCatalogModel>()
    }

    fun isLoading():LiveData<Boolean> = loading
    fun getProductCatalogList():LiveData<MutableList<ProductCatalogModel>> = productCatalogList

    fun getAllProducts(){
        viewModelScope.launch {
            loading.value = true
            val result = repository.getAllProductsFromApi()

            result?.let {
                productCatalogList.value = result.toMutableList()
            }

            loading.value = false
        }
    }

    fun getProductById(id:Int){
        viewModelScope.launch {
            loading.value = true

            val result = repository.getProductByIdFromApi(id)

            result?.let {
                productCatalog.value = result
            }

            loading.value = false
        }
    }
}