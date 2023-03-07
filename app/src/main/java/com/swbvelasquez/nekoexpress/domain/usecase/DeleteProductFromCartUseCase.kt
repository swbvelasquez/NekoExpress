package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.CartRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel

class DeleteProductFromCartUseCase {
    val repository = CartRepository()

    suspend operator fun invoke(productModel:ProductCartModel){
        repository.deleteProductCartToDb(productModel)
    }
}