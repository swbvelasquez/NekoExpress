package com.swbvelasquez.nekoexpress.data.repository

import androidx.room.Transaction
import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.data.database.entity.toCartEntity
import com.swbvelasquez.nekoexpress.data.database.entity.toProductCartEntity
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository {
    /*
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

    suspend fun existProductCartFromDb(cartId:Int,productId:Int):Boolean {
        return cartDao.existProductCart(cartId,productId) > 0
    }

    suspend fun insertCartToDb(cart:CartModel){
        val cartEntity = cart.toCartEntity()

        val result = withContext(Dispatchers.IO){
            cartDao.insertCart(cartEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_ONE)
    }

    suspend fun insertProductCartToDb(product:ProductCartModel){
        val productEntity = product.toProductCartEntity()

        val result = withContext(Dispatchers.IO){
            cartDao.insertProductCart(productEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_ONE)
    }

    @Transaction
    suspend fun insertAllProductsCartToDb(productList:List<ProductCartModel>){
        val productCartEntityList = productList.map { it.toProductCartEntity() }

        val result = withContext(Dispatchers.IO){
            val resultProductCartList = cartDao.insertAllProductsCart(productCartEntityList)

            return@withContext !(resultProductCartList.isEmpty() || resultProductCartList.contains(-1))
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_LIST)
    }

    suspend fun updateCartToDb(cart:CartModel){
        val cartEntity = cart.toCartEntity()

        val result = withContext(Dispatchers.IO){
            cartDao.updateCart(cartEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_UPDATE_ONE)
    }

    suspend fun updateProductCartToDb(product:ProductCartModel){
        val productEntity = product.toProductCartEntity()

        val result = withContext(Dispatchers.IO){
            cartDao.updateProductCart(productEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_UPDATE_ONE)
    }

    suspend fun deleteProductCartToDb(product:ProductCartModel){
        val productEntity = product.toProductCartEntity()

        val result = withContext(Dispatchers.IO){
            cartDao.deleteProductCart(productEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_ONE)
    }

    @Transaction
    suspend fun deleteAllProductsCartToDb(productList:List<ProductCartModel>){
        val productCartEntityList = productList.map { it.toProductCartEntity() }

        val result = withContext(Dispatchers.IO){
            val resultProductCartList = cartDao.deleteAllProductsCart(productCartEntityList)

            return@withContext resultProductCartList <= 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_LIST)
    }

    @Transaction
    suspend fun deleteCartWithProductsToDb(cart:CartModel){
        val cartEntity = cart.toCartEntity()
        val productCartEntityList = cart.productList.map { it.toProductCartEntity() }

        val result = withContext(Dispatchers.IO){
            var resultProductCartList = 1

            if(productCartEntityList.isNotEmpty()){
                resultProductCartList = cartDao.deleteAllProductsCart(productCartEntityList)
            }

            val resultCart = cartDao.deleteCart(cartEntity)

            return@withContext !(resultProductCartList <= 0 || resultCart <= 0)
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_LIST)
    }

     */
}