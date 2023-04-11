package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.data.database.entity.InvoiceDetailEntity
import com.swbvelasquez.nekoexpress.data.database.entity.InvoiceEntity
import com.swbvelasquez.nekoexpress.data.database.model.InvoiceWithDetailDto

@Dao
interface InvoiceDao {
    @Transaction
    @Query("select * from InvoiceTable where userId = :userId")
    suspend fun getAllInvoicesByUserId(userId:Long) : List<InvoiceEntity>?

    @Transaction
    @Query("select * from InvoiceTable where invoiceId = :invoiceId")
    suspend fun getInvoiceWithDetailsById(invoiceId:Long) : InvoiceWithDetailDto?

    @Insert
    suspend fun insertInvoice(invoice:InvoiceEntity):Long

    @Insert
    suspend fun insertInvoiceDetailList(invoiceDetailList:List<InvoiceDetailEntity>):List<Long>

    @Transaction
    suspend fun insertInvoiceWithDetails(invoice:InvoiceEntity,invoiceDetailList:List<InvoiceDetailEntity>){
        val resultInvoice = insertInvoice(invoice)

        if(resultInvoice <= 0)  throw CustomException(CustomTypeException.DB_INSERT_ONE)

        invoiceDetailList.forEach{
            it.invoiceId = resultInvoice
        }

        val resultInvoiceDetailList = insertInvoiceDetailList(invoiceDetailList)

        if(resultInvoiceDetailList.isEmpty() || resultInvoiceDetailList.contains(-1))  throw CustomException(CustomTypeException.DB_INSERT_LIST)
    }
}