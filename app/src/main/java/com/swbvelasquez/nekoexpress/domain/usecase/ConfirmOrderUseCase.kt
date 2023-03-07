package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.CartRepository
import com.swbvelasquez.nekoexpress.data.repository.InvoiceRepository
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.model.InvoiceDetailModel
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel
import com.swbvelasquez.nekoexpress.domain.model.toInvoiceDetailModel

class ConfirmOrderUseCase {
    private val cartRepository = CartRepository()
    private val invoiceRepository = InvoiceRepository()

    suspend operator fun invoke(cart:CartModel){
        val invoice = InvoiceModel()
        var invoiceDetail:InvoiceDetailModel
        val invoiceDetailList:MutableList<InvoiceDetailModel> = mutableListOf()

        cart.productList.forEachIndexed{ index, product ->
            invoiceDetail = product.toInvoiceDetailModel(index+1)
            invoiceDetailList.add(invoiceDetail)
        }

        invoice.detailList = invoiceDetailList
    }
}