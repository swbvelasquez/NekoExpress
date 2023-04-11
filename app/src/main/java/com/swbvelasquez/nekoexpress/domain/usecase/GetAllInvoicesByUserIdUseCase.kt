package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.InvoiceRepository
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel

class GetAllInvoicesByUserIdUseCase {
    private val repository = InvoiceRepository()

    suspend operator fun invoke(userId:Long): List<InvoiceModel>?{
        return repository.getAllInvoicesByUserIdFromDb(userId)
    }
}