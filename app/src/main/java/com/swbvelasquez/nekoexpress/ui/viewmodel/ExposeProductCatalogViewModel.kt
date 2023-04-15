package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.FavoriteProductModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.usecase.*
import kotlinx.coroutines.launch

class ExposeProductCatalogViewModel:ViewModel() {
    private val getProductsByCategoryUseCase = GetProductsByCategoryUseCase()
    private val getAllFavoriteProductsByCategoryUseCase = GetAllFavoriteProductsByCategoryUseCase()
    private val addFavoriteProductUseCase = AddFavoriteProductUseCase()
    private val deleteFavoriteProductUseCase = DeleteFavoriteProductUseCase()
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

    fun getProductsByCategory(userId:Long, category:String){
        viewModelScope.launch {
            loading.value = true

            try{
                val result = getProductsByCategoryUseCase(category)

                result?.let { productCatalogList ->
                    val productCatalogMap = productCatalogList.associateBy { product -> product.productId }
                    val favoriteList = getAllFavoriteProductsByCategoryUseCase(userId,category)

                    favoriteList?.forEach { product ->
                        productCatalogMap[product.productId]?.isFavorite = true
                    }

                    productList.value = productCatalogMap.values.toMutableList()
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

    fun addProductToFavorites(product:FavoriteProductModel){
        viewModelScope.launch {
            try{
                addFavoriteProductUseCase(product)
            }catch (ex:CustomException){
                customException.value = ex
            }catch (ex:Exception){
                customException.value = CustomException(CustomTypeException.UNKNOWN)
            }
        }
    }

    fun deleteProductToFavorites(product:FavoriteProductModel){
        viewModelScope.launch {
            try{
                deleteFavoriteProductUseCase(product)
            }catch (ex:CustomException){
                customException.value = ex
            }catch (ex:Exception){
                customException.value = CustomException(CustomTypeException.UNKNOWN)
            }
        }
    }
}