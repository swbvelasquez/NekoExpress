package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.FavoriteProductModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.model.UserModel
import com.swbvelasquez.nekoexpress.domain.model.toProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.usecase.DeleteFavoriteProductUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetAllFavoriteProductsByUserIdUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExposeFavoriteProductViewModel(private val userId:Long):ViewModel() {
    private val getAllFavoriteProductsByUserIdUseCase = GetAllFavoriteProductsByUserIdUseCase()
    private val deleteFavoriteProductUseCase = DeleteFavoriteProductUseCase()
    private val loading = MutableLiveData<Boolean>()

    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }

    val favoriteProductList : LiveData<List<ProductCatalogModel>> = liveData {
        try{
            loading.value = true

            val productDtoList = getAllFavoriteProductsByUserIdUseCase(userId)

            delay(250)

            if (productDtoList != null) {
                emitSource(productDtoList.map { productModelList ->
                    productModelList.map { it.toProductCatalogModel() }
                })
            }
        }catch (ex:CustomException){
            customException.value = ex
        }catch (ex:Exception){
            customException.value = CustomException(CustomTypeException.UNKNOWN)
        }finally {
            loading.value = false
        }
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException

    fun deleteProductToFavorites(product: FavoriteProductModel){
        viewModelScope.launch {
            try{
                loading.value = true

                deleteFavoriteProductUseCase(product)
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
class ExposeFavoriteProductViewModelFactory(private val userId:Long) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExposeFavoriteProductViewModel(userId) as T
    }
}