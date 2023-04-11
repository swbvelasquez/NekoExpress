package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.swbvelasquez.nekoexpress.domain.model.InvoiceDetailModel

@Entity(
    tableName = "InvoiceDetailTable",
    primaryKeys = ["invoiceId","productId","invoiceDetailId"],
    indices = [Index(name = "invoiceDetail_product_fk_idx",value = ["productId"])],
    foreignKeys = [
        ForeignKey(
            entity = InvoiceEntity::class,
            parentColumns = ["invoiceId"],
            childColumns = ["invoiceId"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            onDelete = ForeignKey.RESTRICT
        )
    ])
data class InvoiceDetailEntity(
    @ColumnInfo(name="invoiceDetailId") val invoiceDetailId:Int,
    @ColumnInfo(name="productId") val productId:Long,
    @ColumnInfo(name="invoiceId") var invoiceId:Long,
    @ColumnInfo(name="title") val title:String,
    @ColumnInfo(name="price") val price:Double,
    @ColumnInfo(name="image") val image:String,
    @ColumnInfo(name="quantity") val quantity:Int,
    @ColumnInfo(name="total") val total:Double,
    @ColumnInfo(name="size") val size:String,
    @ColumnInfo(name="color") val color:String
)

fun InvoiceDetailModel.toInvoiceDetailEntity() =
    InvoiceDetailEntity(invoiceDetailId=invoiceDetailId,productId=productId,invoiceId=invoiceId,title=title,price=price,image=image,quantity=quantity,total=total,size=size,color=color)