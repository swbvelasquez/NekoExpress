package com.swbvelasquez.nekoexpress.data.repository

import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.data.database.entity.toInvoiceDetailEntity
import com.swbvelasquez.nekoexpress.data.database.entity.toInvoiceEntity
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel
import com.swbvelasquez.nekoexpress.domain.model.toInvoiceModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InvoiceRepository {
    private val invoiceDao = NekoApplication.database.getInvoiceDao()

    suspend fun getAllInvoicesByUserIdFromDb(userId:Long):List<InvoiceModel>?{
        val invoiceEntityList = withContext(Dispatchers.IO){ invoiceDao.getAllInvoicesByUserId(userId) }
        var invoiceModelList:List<InvoiceModel>?=null

        if(!invoiceEntityList.isNullOrEmpty()){
            invoiceModelList = invoiceEntityList.map { it.toInvoiceModel() }
        }

        return invoiceModelList
    }

    suspend fun getInvoiceWithDetailsByIdFromDb(invoiceId:Long):InvoiceModel?{
        val invoiceEntity = withContext(Dispatchers.IO){ invoiceDao.getInvoiceWithDetailsById(invoiceId) }
        var invoiceModel:InvoiceModel? = null

        if(invoiceEntity!=null){
            invoiceModel = invoiceEntity.toInvoiceModel()
        }

        return invoiceModel
    }

    suspend fun insertInvoiceWithDetailsToDb(invoiceModel: InvoiceModel){
        val invoiceEntity = invoiceModel.toInvoiceEntity()
        val invoiceDetailEntityList = invoiceModel.invoiceDetailList.map { it.toInvoiceDetailEntity() }

        withContext(Dispatchers.IO) {
            invoiceDao.insertInvoiceWithDetails(invoiceEntity, invoiceDetailEntityList)
        }
    }
}