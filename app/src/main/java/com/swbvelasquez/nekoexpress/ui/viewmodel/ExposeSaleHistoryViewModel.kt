package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetAllInvoicesByUserIdUseCase
import kotlinx.coroutines.launch

class ExposeSaleHistoryViewModel():ViewModel() {
    private val getAllInvoicesByUserIdUseCase = GetAllInvoicesByUserIdUseCase()

    private val loading = MutableLiveData<Boolean>()

    private val invoiceList : MutableLiveData<MutableList<InvoiceModel>> by lazy {
        MutableLiveData<MutableList<InvoiceModel>>()
    }
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getInvoiceList(): LiveData<MutableList<InvoiceModel>> = invoiceList

    fun getAllInvoiceList(userId:Long){
        viewModelScope.launch {
            loading.value = true

            try{
                val result = getAllInvoicesByUserIdUseCase(userId)

                result?.let {
                    invoiceList.value = it.toMutableList()
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