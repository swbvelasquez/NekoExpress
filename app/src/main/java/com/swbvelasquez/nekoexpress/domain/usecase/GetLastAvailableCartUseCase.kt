package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.core.enum.CartStatus
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.data.repository.CartRepository
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import java.util.*

class GetLastAvailableCartUseCase {
    private val repository = CartRepository()

    suspend operator fun invoke():CartModel?{
        val cartModel = repository.getLastCartWithProductsFromDb()

        if(cartModel!=null){
            val currentDateTime = Calendar.getInstance().time
            val differenceInMS = cartModel.date - currentDateTime.time

            if(differenceInMS >= Constants.MAX_TIME_AVAILABLE_CART){
                cartModel.status = CartStatus.CANCELED
                repository.updateCartToDb(cartModel)
            }
        }

        return cartModel
    }
}