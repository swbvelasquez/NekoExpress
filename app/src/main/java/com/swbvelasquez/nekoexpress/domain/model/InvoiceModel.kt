package com.swbvelasquez.nekoexpress.domain.model

data class InvoiceModel(
    var invoiceId:Long=0,
    var userId:Long=0,
    var cartId:Long=0,
    var subtotal:Double=0.0,
    var taxes:Double=0.0,
    var total:Double=0.0,
    var detailList:MutableList<InvoiceDetailModel> = mutableListOf()
)
