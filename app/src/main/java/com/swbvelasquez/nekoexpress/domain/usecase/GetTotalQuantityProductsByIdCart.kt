package com.swbvelasquez.nekoexpress.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.swbvelasquez.nekoexpress.data.repository.CartRepository

class GetTotalQuantityProductsByIdCart {
    private val repository = CartRepository()

    operator fun invoke(cartId:Long):LiveData<Int> {
        return repository.getTotalQuantityProductsByIdCart(cartId)
    }
}