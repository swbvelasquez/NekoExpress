package com.swbvelasquez.nekoexpress.data.repository

import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.data.network.service.ProductCatalogService
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.model.toProductCatalogModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductCatalogRepository {
    private val service = ProductCatalogService()
    private val dao = NekoApplication.database.getProductCatalogDao()

    suspend fun getAllProductsFromApi():List<ProductCatalogModel>?{
        val productDTOList = service.getAllProducts()
        var productModelList:MutableList<ProductCatalogModel>? = null

        if(!productDTOList.isNullOrEmpty()){
            productModelList = productDTOList.map { it.toProductCatalogModel() }.toMutableList()
        }

        return productModelList
    }

    suspend fun getAllProductsFromDatabase():List<ProductCatalogModel>?{
        val productEntityList = withContext(Dispatchers.IO){ dao.getAllProductsWithRanking() }
        var productModelList:MutableList<ProductCatalogModel>? = null

        if(!productEntityList.isNullOrEmpty()){
            productModelList = productEntityList.map { it.toProductCatalogModel() }.toMutableList()
        }

        return productModelList
    }

    suspend fun getProductByIdFromApi(id:Int):ProductCatalogModel?{
        val productDTO = service.getProductById(id)
        var productModel:ProductCatalogModel? = null

        if(productDTO !=null){
            productModel = productDTO.toProductCatalogModel()
        }

        return productModel
    }
}