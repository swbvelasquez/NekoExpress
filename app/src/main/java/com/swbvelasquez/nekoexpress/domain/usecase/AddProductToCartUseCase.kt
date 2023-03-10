package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.CartRepository
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel

class AddProductToCartUseCase {
    private val repository = CartRepository()

    suspend operator fun invoke(newProduct: ProductCartModel) {
        val cart = repository.getCartWithProductsFromDb(newProduct.cartId)

        cart?.let {
            val index = cart.productList.indexOfFirst { p -> p.productId == newProduct.productId && p.size == newProduct.size && p.color == newProduct.color }
            val productCart: ProductCartModel

            if (index >= 0) {
                productCart = cart.productList[index].copy()
                productCart.apply {
                    quantity++
                    total = quantity * price
                }

                repository.updateProductCartToDb(productCart)
            } else {
                val newProductId = cart.productList.size.toLong().plus(1)
                productCart = newProduct.copy(productCartId = newProductId, total = newProduct.price)
                repository.insertProductCartToDb(productCart)
            }
        }
    }
}