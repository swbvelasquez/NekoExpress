package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.core.enum.CartStatus
import com.swbvelasquez.nekoexpress.data.repository.CartRepository
import com.swbvelasquez.nekoexpress.data.repository.InvoiceRepository
import com.swbvelasquez.nekoexpress.domain.model.*

class ConfirmOrderUseCase {
    private val cartRepository = CartRepository()
    private val invoiceRepository = InvoiceRepository()

    suspend operator fun invoke(cart:CartModel, deliveryAddress:DeliveryAddressModel){
        invoiceRepository.insertInvoiceToDb(cart.toInvoiceModel(deliveryAddress))
        cart.status = CartStatus.PAID
        cartRepository.updateCartToDb(cart)
    }
}