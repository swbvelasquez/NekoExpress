package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.swbvelasquez.nekoexpress.core.enum.CartStatus
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.data.database.entity.CartEntity
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartCrossRefEntity
import com.swbvelasquez.nekoexpress.data.database.model.CartWithProductDto

@Dao
interface CartDao {
    @Transaction
    @Query("select * from CartTable where cartId = :cartId")
    suspend fun getCartWithProducts(cartId:Int) : CartWithProductDto?

    @Transaction
    @Query("select * from CartTable where cartId = (select max(cartId) from CartTable) and status = ${CartStatus.PENDING}")
    suspend fun getLastCartWithProducts() : CartWithProductDto?

    @Query("select * from ProductCartTable where cartId = :cartId and productId = :productId")
    suspend fun getProductCart(cartId:Long,productId:Long) : ProductCartCrossRefEntity?

    @Query("select count(1) from ProductCartTable where cartId = :cartId and productId = :productId")
    suspend fun existProductCart(cartId:Long,productId:Long) : Int

    @Insert
    suspend fun insertCart(cart:CartEntity):Long

    @Insert
    suspend fun insertProductCart(productCart:ProductCartCrossRefEntity):Long

    @Transaction
    @Insert
    suspend fun insertAllProductsCart(productCartList:List<ProductCartCrossRefEntity>):List<Long>

    @Update
    suspend fun updateCart(cart:CartEntity):Int

    @Update
    suspend fun updateProductCart(productCart:ProductCartCrossRefEntity):Int

    @Transaction
    @Update
    suspend fun updateAllProductsCart(productCartList:List<ProductCartCrossRefEntity>):Int

    @Delete
    suspend fun deleteCart(cart:CartEntity):Int

    @Delete
    suspend fun deleteProductCart(productCart:ProductCartCrossRefEntity):Int

    @Transaction
    @Delete
    suspend fun deleteAllProductsCart(productCartList:List<ProductCartCrossRefEntity>):Int
}