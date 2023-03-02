package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.swbvelasquez.nekoexpress.data.database.entity.CartEntity
import com.swbvelasquez.nekoexpress.data.database.entity.ProductCartEntityCrossRef
import com.swbvelasquez.nekoexpress.data.database.model.CartWithProductDto

@Dao
interface CartDao {
    @Transaction
    @Query("select * from CartTable where id = :cartId")
    suspend fun getCartWithProducts(cartId:Int) : CartWithProductDto?

    @Transaction
    @Query("select * from CartTable where id = (select max(id) from CartTable)")
    suspend fun getLastCartWithProducts() : CartWithProductDto?

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

    @Delete
    suspend fun deleteCart(cart:CartEntity):Int

    @Delete
    suspend fun deleteProductCart(productCart:ProductCartEntityCrossRef):Int

}