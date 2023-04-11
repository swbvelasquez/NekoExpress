package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.InvoiceDetailEntity

data class InvoiceDetailModel(
    val invoiceDetailId:Int,
    val productId:Long,
    var invoiceId:Long=0,
    val title:String,
    val price:Double,
    val image:String,
    val quantity:Int,
    val total:Double,
    val size:String,
    val color:String
)

fun ProductCartModel.toInvoiceDetailModel(invoiceDetailId:Int=0) =
    InvoiceDetailModel(invoiceDetailId=invoiceDetailId,productId=productId,title=title,price=price,image=image,quantity=quantity,total=total,size=size,color=color)

fun InvoiceDetailEntity.toInvoiceDetailModel() =
    InvoiceDetailModel(invoiceDetailId=invoiceDetailId,productId=productId,title=title,price=price,image=image,quantity=quantity,total=total,size=size,color=color)