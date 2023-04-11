package com.swbvelasquez.nekoexpress.data.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.swbvelasquez.nekoexpress.data.database.entity.InvoiceDetailEntity
import com.swbvelasquez.nekoexpress.data.database.entity.InvoiceEntity

data class InvoiceWithDetailDto(
    @Embedded val invoice:InvoiceEntity,
    @Relation(
        parentColumn = "invoiceId",
        entityColumn = "invoiceId",
        entity = InvoiceDetailEntity::class
    )
    val invoiceDetailList:List<InvoiceDetailEntity>
)