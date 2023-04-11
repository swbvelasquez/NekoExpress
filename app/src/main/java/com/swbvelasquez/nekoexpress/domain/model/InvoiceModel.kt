package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.InvoiceEntity
import com.swbvelasquez.nekoexpress.data.database.model.InvoiceWithDetailDto
import java.util.Calendar

data class InvoiceModel(
    val invoiceId:Long=0,
    val userId:Long=0,
    val cartId:Long=0,
    val subtotal:Double=0.0,
    val taxes:Double=0.0,
    val total:Double=0.0,
    val date:Long,
    val deliveryAddress: DeliveryAddressModel,
    var invoiceDetailList:List<InvoiceDetailModel> = mutableListOf()
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
        date = Calendar.getInstance().time.time,
        deliveryAddress = deliveryAddress,
        invoiceDetailList = invoiceDetailList)
}

fun InvoiceWithDetailDto.toInvoiceModel() = InvoiceModel(invoiceId=invoice.invoiceId,userId=invoice.userId,cartId=invoice.cartId,subtotal=invoice.subtotal,taxes=invoice.taxes,total=invoice.total,date=invoice.date
    ,DeliveryAddressModel(department=invoice.department,province=invoice.province,district=invoice.district,address=invoice.address,phone=invoice.phone)
    ,invoiceDetailList=invoiceDetailList.map { it.toInvoiceDetailModel() })

fun InvoiceEntity.toInvoiceModel() = InvoiceModel(invoiceId=invoiceId,userId=userId,cartId=cartId,subtotal=subtotal,taxes=taxes,total=total,date=date
    ,DeliveryAddressModel(department=department,province=province,district=district,address=address,phone=phone)
    ,invoiceDetailList= mutableListOf())