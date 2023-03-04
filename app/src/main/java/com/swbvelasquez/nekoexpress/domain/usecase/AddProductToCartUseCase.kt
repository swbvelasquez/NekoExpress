package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.CartRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel

class AddProductToCartUseCase {
    private val repository = CartRepository()

    suspend operator fun invoke(productModel:ProductCartModel){
        val existProduct = repository.existProductCartFromDb(productModel.cartId, productModel.productId)

        if(existProduct){
            repository.updateProductCartToDb(productModel)
        }else{
            repository.insertProductCartToDb(productModel)
        }
    }
}