package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.*
import com.swbvelasquez.nekoexpress.domain.model.DeliveryAddressModel
import com.swbvelasquez.nekoexpress.domain.model.InvoiceDetailModel
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel

@Entity(
    tableName = "InvoiceTable",
    indices = [Index(name = "invoice_cart_fk_idx",value = ["cartId"])],
    foreignKeys = [
        ForeignKey(
            entity = CartEntity::class,
            parentColumns = ["cartId"],
            childColumns = ["cartId"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class InvoiceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="invoiceId") val invoiceId:Long,
    @ColumnInfo(name="userId") val userId:Long,
    @ColumnInfo(name="cartId") val cartId:Long,
    @ColumnInfo(name="subtotal") val subtotal:Double,
    @ColumnInfo(name="taxes") val taxes:Double,
    @ColumnInfo(name="total") val total:Double,
    @ColumnInfo(name="date") val date:Long,
    @ColumnInfo(name="department") val department:String,
    @ColumnInfo(name="province") val province:String,
    @ColumnInfo(name="district") val district:String,
    @ColumnInfo(name="address") val address:String,
    @ColumnInfo(name="phone") val phone:String
)

fun InvoiceModel.toInvoiceEntity() =
    InvoiceEntity(invoiceId=invoiceId,userId=userId,cartId=cartId,subtotal=subtotal,taxes=taxes,total=total,date=date
        ,department=deliveryAddress.department,province=deliveryAddress.province,district=deliveryAddress.district,address=deliveryAddress.address,phone=deliveryAddress.phone)