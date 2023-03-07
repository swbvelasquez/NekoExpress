package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.CartRepository
import com.swbvelasquez.nekoexpress.domain.model.CartModel

class ProceedToPayCartUseCase {
    private val repository = CartRepository()

    suspend operator fun invoke(cart:CartModel){
        if(cart.productList.isNotEmpty()){
            repository.deleteAllProductsCartToDb(cart.productList)
            repository.insertAllProductsCartToDb(cart.productList)
        }
    }
}