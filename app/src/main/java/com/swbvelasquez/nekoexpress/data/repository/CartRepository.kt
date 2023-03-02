package com.swbvelasquez.nekoexpress.data.repository

import androidx.room.Transaction
import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Functions.toJson
import com.swbvelasquez.nekoexpress.data.database.entity.toCartEntity
import com.swbvelasquez.nekoexpress.data.database.entity.toProductCartEntity
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.domain.model.toCartModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository {
    private val cartDao = NekoApplication.database.getCartDao()

    suspend fun getCartWithProductsFromDb(cartId:Int):CartModel? {
        val cartDto = cartDao.getCartWithProducts(cartId)
        var cartModel : CartModel? = null

        if(cartDto!=null){
            cartModel = cartDto.toCartModel()
        }

        return cartModel
    }

    suspend fun getLastCartWithProductsFromDb():CartModel? {
        val cartDto = cartDao.getLastCartWithProducts()
        var cartModel : CartModel? = null

        if(cartDto!=null){
            cartModel = cartDto.toCartModel()
        }

        return cartModel
    }

    @Transaction
    suspend fun insertCartWithProductsToDb(cart:CartModel){
        val cartEntity = cart.toCartEntity()
        val productCartEntityList = cart.productList.map { it.toProductCartEntity() }

        val result = withContext(Dispatchers.IO){
            val resultCart = cartDao.insertCart(cartEntity)
            val resultProductCartList = cartDao.insertAllProductsCart(productCartEntityList)

            return@withContext !(resultProductCartList.isEmpty() || resultProductCartList.contains(-1) || resultCart <= 0)
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_LIST)
    }

    suspend fun insertProductCartToDb(product:ProductCartModel){
        val productEntity = product.toProductCartEntity()

        val result = withContext(Dispatchers.IO){
            cartDao.insertProductCart(productEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_ONE)
    }

}