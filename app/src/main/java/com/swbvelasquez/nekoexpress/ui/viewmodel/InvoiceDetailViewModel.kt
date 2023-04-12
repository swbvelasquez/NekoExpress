package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.*
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel
import com.swbvelasquez.nekoexpress.domain.usecase.GetInvoiceByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InvoiceDetailViewModel(private val invoiceId:Long):ViewModel() {
    private val getInvoiceByIdUseCase = GetInvoiceByIdUseCase()
    private val loading = MutableLiveData<Boolean>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }
    private val invoice : MutableLiveData<InvoiceModel> by lazy {
        MutableLiveData<InvoiceModel>()
    }

    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getInvoice(): LiveData<InvoiceModel> = invoice

    init {
        viewModelScope.launch {
            loading.value = true

            try{
                val invoiceResult = getInvoiceByIdUseCase(invoiceId)

                withContext(Dispatchers.IO){
                    delay(250)
                }

                invoiceResult?.let {
                    invoice.value = invoiceResult
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

@Suppress("UNCHECKED_CAST")
class InvoiceDetailViewModelFactory(private val invoiceId:Long) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InvoiceDetailViewModel(invoiceId) as T
    }
}