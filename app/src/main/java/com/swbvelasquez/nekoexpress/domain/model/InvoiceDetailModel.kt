package com.swbvelasquez.nekoexpress.domain.model

data class InvoiceDetailModel(
    var invoiceId:Long=0,
    val productId:Long,
    val order:Int=0,
    val quantity:Int,
    val total:Double
)

fun ProductCartModel.toInvoiceDetailModel(order:Int=0) = InvoiceDetailModel(productId=productId,order=order,quantity=quantity,total=total)