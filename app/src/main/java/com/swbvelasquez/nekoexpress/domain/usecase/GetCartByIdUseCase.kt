package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.CartRepository
import com.swbvelasquez.nekoexpress.domain.model.CartModel

class GetCartByIdUseCase {
    private val repository = CartRepository()

    suspend operator fun invoke(cartId:Long):CartModel?{
        return repository.getCartWithProductsFromDb(cartId)
    }
}