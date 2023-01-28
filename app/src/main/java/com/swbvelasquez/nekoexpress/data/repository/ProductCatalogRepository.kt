package com.swbvelasquez.nekoexpress.data.repository

import androidx.room.Transaction
import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.data.database.entity.toProductCatalogEntity
import com.swbvelasquez.nekoexpress.data.database.entity.toRatingEntity
import com.swbvelasquez.nekoexpress.data.network.service.ProductCatalogService
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.model.toProductCatalogModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductCatalogRepository {
    private val productApi = ProductCatalogService()
    private val productDao = NekoApplication.database.getProductCatalogDao()
    private val ratingDao = NekoApplication.database.getRatingDao()

    suspend fun getAllProductsFromApi():List<ProductCatalogModel>?{
        val productDTOList = productApi.getAllProducts()
        var productModelList:MutableList<ProductCatalogModel>? = null

        if(!productDTOList.isNullOrEmpty()){
            productModelList = productDTOList.map { it.toProductCatalogModel() }.toMutableList()
        }

        return productModelList
    }

    suspend fun getAllProductsFromDatabase():List<ProductCatalogModel>?{
        val productEntityList = withContext(Dispatchers.IO){ productDao.getAllProductsWithRanking() }
        var productModelList:MutableList<ProductCatalogModel>? = null

        if(!productEntityList.isNullOrEmpty()){
            productModelList = productEntityList.map { it.toProductCatalogModel() }.toMutableList()
        }

        return productModelList
    }

    suspend fun getProductByIdFromApi(id:Int):ProductCatalogModel?{
        val productDTO = productApi.getProductById(id)
        var productModel:ProductCatalogModel? = null

        if(productDTO !=null){
            productModel = productDTO.toProductCatalogModel()
        }

        return productModel
    }

    suspend fun getProductByIdFromDatabase(id:Int):ProductCatalogModel?{
        val productEntity = withContext(Dispatchers.IO){ productDao.getProductWithRankingById(id) }
        var productModel:ProductCatalogModel? = null

        if(productEntity !=null){
            productModel = productEntity.toProductCatalogModel()
        }

        return productModel
    }

    @Transaction
    suspend fun insertProductToDatabase(productModel:ProductCatalogModel){
        val productEntity = productModel.toProductCatalogEntity()
        val ratingEntity = productModel.rating.toRatingEntity(productModel.id)
        val result = withContext(Dispatchers.IO){
            productDao.insertProduct(productEntity)>0 && ratingDao.insertRating(ratingEntity)>0
        }
    }
}