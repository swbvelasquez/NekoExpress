package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.swbvelasquez.nekoexpress.core.enum.CartStatus
import com.swbvelasquez.nekoexpress.data.database.entity.CartEntity
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartEntityCrossRef
import com.swbvelasquez.nekoexpress.data.database.model.CartWithProductDto

@Dao
interface CartDao {
    @Transaction
    @Query("select * from CartTable where id = :cartId")
    suspend fun getCartWithProducts(cartId:Int) : CartWithProductDto?

    @Transaction
    @Query("select * from CartTable where id = (select max(id) from CartTable) and status = ${CartStatus.PENDING}")
    suspend fun getLastCartWithProducts() : CartWithProductDto?

    @Query("select * from ProductCartTable where cartId = :cartId and productId = :productId")
    suspend fun getProductCart(cartId:Int,productId:Int) : ProductCartEntityCrossRef?

    @Query("select count(1) from ProductCartTable where cartId = :cartId and productId = :productId")
    suspend fun existProductCart(cartId:Int,productId:Int) : Int

    @Insert
    suspend fun insertCart(cart:CartEntity):Long

    @Insert
    suspend fun insertProductCart(productCart:ProductCartEntityCrossRef):Long

    @Insert
    suspend fun insertAllProductsCart(productCartList:List<ProductCartEntityCrossRef>):List<Long>

    @Update
    suspend fun updateCart(cart:CartEntity):Int

    @Update
    suspend fun updateProductCart(productCart:ProductCartEntityCrossRef):Int

    @Update
    suspend fun updateAllProductsCart(productCartList:List<ProductCartEntityCrossRef>):Int

    @Delete
    suspend fun deleteCart(cart:CartEntity):Int

    @Delete
    suspend fun deleteProductCart(productCart:ProductCartEntityCrossRef):Int

    @Delete
    suspend fun deleteAllProductsCart(productCartList:List<ProductCartEntityCrossRef>):Int
}