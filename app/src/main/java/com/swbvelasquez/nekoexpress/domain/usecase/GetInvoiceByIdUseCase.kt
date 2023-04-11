package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.InvoiceRepository
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel

class GetInvoiceByIdUseCase {
    private val repository = InvoiceRepository()

    suspend operator fun invoke(invoiceId:Long): InvoiceModel?{
        return repository.getInvoiceWithDetailsByIdFromDb(invoiceId)
    }
}