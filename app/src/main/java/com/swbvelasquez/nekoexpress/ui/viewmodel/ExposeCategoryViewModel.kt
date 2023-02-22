package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetAllCategoriesUseCase
import kotlinx.coroutines.launch

class ExposeCategoryViewModel:ViewModel() {
    private val getAllCategoriesUseCase = GetAllCategoriesUseCase()
    private val loading = MutableLiveData<Boolean>()

    private val categoryList : MutableLiveData<MutableList<CategoryModel>> by lazy {
        MutableLiveData<MutableList<CategoryModel>>()
    }
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException():LiveData<CustomException> = customException
    fun getCategoryList():LiveData<MutableList<CategoryModel>> = categoryList

    fun getAllCategories(){
        viewModelScope.launch {
            loading.value = true

            try{
                val result = getAllCategoriesUseCase()

                result?.let {
                    categoryList.value = it.toMutableList()
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