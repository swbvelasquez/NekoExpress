package com.swbvelasquez.nekoexpress.domain.model

data class InvoiceModel(
    var invoiceId:Long=0,
    var userId:Long=0,
    var cartId:Long=0,
    var subtotal:Double=0.0,
    var taxes:Double=0.0,
    var total:Double=0.0,
    var deliveryAddress: DeliveryAddressModel,
    var detailList:MutableList<InvoiceDetailModel> = mutableListOf()
)

fun CartModel.toInvoiceModel(deliveryAddress:DeliveryAddressModel):InvoiceModel {
    var invoiceDetail:InvoiceDetailModel
    val invoiceDetailList:MutableList<InvoiceDetailModel> = mutableListOf()

    productList.forEachIndexed{ index, product ->
        invoiceDetail = product.toInvoiceDetailModel(index+1)
        invoiceDetailList.add(invoiceDetail)
    }

    return InvoiceModel(
        userId = userId,
        cartId = cartId,
        subtotal = subtotal,
        taxes = taxes,
        total = total,
        deliveryAddress = deliveryAddress,
        detailList = invoiceDetailList)
}