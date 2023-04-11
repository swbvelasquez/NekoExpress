package com.swbvelasquez.nekoexpress.domain.usecase

import androidx.lifecycle.LiveData
import com.swbvelasquez.nekoexpress.data.repository.CartRepository

class GetTotalQuantityProductsByUserId {
    private val repository = CartRepository()

    operator fun invoke(cartId:Long):LiveData<Int> {
        return repository.getTotalQuantityProductsByUserId(cartId)
    }
}