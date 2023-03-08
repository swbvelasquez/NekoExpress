package com.swbvelasquez.nekoexpress.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.model.toProductCartModel
import com.swbvelasquez.nekoexpress.domain.usecase.AddProductToCartUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetCartByIdUseCase
import com.swbvelasquez.nekoexpress.domain.usecase.GetProductByIdUseCase
import kotlinx.coroutines.launch

class DetailProductCatalogViewModel:ViewModel() {
    private val addProductToCartUseCase = AddProductToCartUseCase()
    private val getProductByIdUseCase = GetProductByIdUseCase()
    private val getCartByIdUseCase = GetCartByIdUseCase()

    private val loading = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any?>()
    private val customException : MutableLiveData<CustomException> by lazy {
        MutableLiveData<CustomException>()
    }
    private val productModel : MutableLiveData<ProductCatalogModel> by lazy {
        MutableLiveData<ProductCatalogModel>()
    }
    private val cartModel : MutableLiveData<CartModel> by lazy {
        MutableLiveData<CartModel>()
    }
    fun isLoading(): LiveData<Boolean> = loading
    fun getTypeException(): LiveData<CustomException> = customException
    fun getProduct():LiveData<ProductCatalogModel> = productModel
    fun getCart():LiveData<CartModel> = cartModel
    fun getResult():LiveData<Any?> = result

    fun setInitValues(productId:Long,cartId:Long){
        viewModelScope.launch {
            loading.value = true

            try {
                val product = getProductByIdUseCase(productId)
                productModel.value = product
                val cart = getCartByIdUseCase(cartId)
                cartModel.value = cart
                result.value = product
            }catch (ex:CustomException){
                customException.value = ex
            }catch (ex:Exception){
                customException.value = CustomException(CustomTypeException.UNKNOWN)
            }finally {
                loading.value = false
            }
        }
    }

    fun addProductToCart(selectedSize:String, selectedColor:String){
        viewModelScope.launch {
            loading.value = true

            try{
                val cart = cartModel.value?.copy()
                val product = productModel.value?.copy()

                cart?.let {
                    product?.let {
                        val index = cart.productList.indexOfFirst { p -> p.productId == product.productId && p.size == selectedSize && p.color == selectedColor }
                        val productCart:ProductCartModel

                        if(index >= 0){
                            productCart = cart.productList[index]
                            productCart.apply {
                                quantity++
                                total=quantity*price
                            }
                        }else{
                            val productCartId = cart.productList.size.toLong().plus(1)
                            productCart = product.toProductCartModel(productCartId,cart.cartId).apply {
                                total=quantity*price
                                size=selectedSize
                                color=selectedColor
                            }
                            cart.productList.add(productCart)
                        }

                        addProductToCartUseCase(productCart)
                        cartModel.value = cart
                        result.value = productCart
                    }
                }
            }catch (ex:CustomException){
                customException.value = ex
            }catch (ex:Exception){
                customException.value = CustomException(CustomTypeException.UNKNOWN)
            }finally {
                loading.value = false
            }
        }
    }
}