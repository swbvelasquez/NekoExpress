package com.swbvelasquez.nekoexpress.data.repository

import androidx.lifecycle.LiveData
import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.data.database.entity.toCartEntity
import com.swbvelasquez.nekoexpress.data.database.entity.toProductCartEntity
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.domain.model.toCartModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository {
    private val cartDao = NekoApplication.database.getCartDao()

    suspend fun getCartWithProductsByIdFromDb(cartId:Long):CartModel? {
        val cartDto = cartDao.getCartWithProductsById(cartId)
        var cartModel : CartModel? = null

        if(cartDto!=null){
            cartModel = cartDto.toCartModel()
        }

        return cartModel
    }

    suspend fun getLastCartWithProductsFromDb(userId:Long):CartModel? {
        val cartDto = cartDao.getLastCartWithProducts(userId)
        var cartModel : CartModel? = null

        if(cartDto!=null){
            cartModel = cartDto.toCartModel()
        }

        return cartModel
    }

    fun getTotalQuantityProductsByUserId(userId: Long) : LiveData<Int>{
        return cartDao.getTotalQuantityProductsByUserId(userId)
    }

    suspend fun existProductCartFromDb(cartId:Long,productId:Long):Boolean {
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

    suspend fun insertProductCartListToDb(productList:List<ProductCartModel>){
        val productCartEntityList = productList.map { it.toProductCartEntity() }

        val result = withContext(Dispatchers.IO){
            val resultProductCartList = cartDao.insertProductCartList(productCartEntityList)

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

    suspend fun updateProductCartListToDb(productList:List<ProductCartModel>){
        val productCartEntityList = productList.map { it.toProductCartEntity() }

        val result = withContext(Dispatchers.IO){
            cartDao.updateProductCartList(productCartEntityList) == productCartEntityList.size
        }

        if(!result) throw CustomException(CustomTypeException.DB_UPDATE_LIST)
    }

    suspend fun deleteProductCartToDb(product:ProductCartModel){
        val productEntity = product.toProductCartEntity()

        val result = withContext(Dispatchers.IO){
            cartDao.deleteProductCart(productEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_ONE)
    }

    suspend fun deleteProductCartListToDb(productList:List<ProductCartModel>){
        val productCartEntityList = productList.map { it.toProductCartEntity() }

        val result = withContext(Dispatchers.IO){
            cartDao.deleteProductCartList(productCartEntityList) == productCartEntityList.size
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_LIST)
    }
}