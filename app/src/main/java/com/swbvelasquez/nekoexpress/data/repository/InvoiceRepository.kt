package com.swbvelasquez.nekoexpress.data.repository

import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel

class InvoiceRepository {
    suspend fun getInvoiceByIdFromDb(invoiceId:Long):InvoiceModel?{
        return null
    }

    suspend fun insertInvoiceToDb(invoiceModel: InvoiceModel){

    }

    suspend fun deleteInvoiceFromDb(invoiceModel: InvoiceModel){

    }
}