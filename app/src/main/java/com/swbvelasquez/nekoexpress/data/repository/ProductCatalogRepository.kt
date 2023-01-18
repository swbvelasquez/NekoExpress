package com.swbvelasquez.nekoexpress.data.repository

import com.swbvelasquez.nekoexpress.data.network.service.ProductCatalogService
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.model.toProductCatalogModel

class ProductCatalogRepository {
    private val service = ProductCatalogService()

    suspend fun getAllProducts():List<ProductCatalogModel>?{
        val productDTOList = service.getAllProducts()
        var productModelList:MutableList<ProductCatalogModel>? = null

        if(!productDTOList.isNullOrEmpty()){
            productModelList = productDTOList.map { it.toProductCatalogModel() }.toMutableList()
        }

        return productModelList
    }

    suspend fun getProductById(id:Int):ProductCatalogModel?{
        val productDTO = service.getProductById(id)
        var productModel:ProductCatalogModel? = null

        if(productDTO !=null){
            productModel = productDTO.toProductCatalogModel()
        }

        return productModel
    }
}