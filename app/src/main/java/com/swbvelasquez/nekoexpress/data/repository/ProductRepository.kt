package com.swbvelasquez.nekoexpress.data.repository

import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.data.database.entity.toProductEntity
import com.swbvelasquez.nekoexpress.data.database.entity.toRatingEntity
import com.swbvelasquez.nekoexpress.data.network.service.ProductService
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.model.toProductCatalogModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository {
    private val productApi = ProductService()
    private val productDao = NekoApplication.database.getProductDao()

    suspend fun getAllProductsFromApi():List<ProductCatalogModel>?{
        val productDTOList = productApi.getAllProducts()
        var productModelList:List<ProductCatalogModel>? = null


        if(!productDTOList.isNullOrEmpty()){
            productModelList = productDTOList.map { it.toProductCatalogModel() }
        }

        return productModelList
    }

    suspend fun getAllProductsFromDb():List<ProductCatalogModel>?{
        val productEntityList = withContext(Dispatchers.IO){ productDao.getAllProductsWithRanking() }
        var productModelList:List<ProductCatalogModel>? = null

        if(!productEntityList.isNullOrEmpty()){
            productModelList = productEntityList.map { it.toProductCatalogModel() }
        }

        return productModelList
    }

    suspend fun getProductByIdFromApi(productId:Long):ProductCatalogModel?{
        val productDTO = productApi.getProductById(productId)
        var productModel:ProductCatalogModel? = null

        if(productDTO !=null){
            productModel = productDTO.toProductCatalogModel()
        }

        return productModel
    }

    suspend fun getProductByIdFromDb(productId:Long):ProductCatalogModel?{
        val productEntity = withContext(Dispatchers.IO){ productDao.getProductWithRankingById(productId) }
        var productModel:ProductCatalogModel? = null

        if(productEntity !=null){
            productModel = productEntity.toProductCatalogModel()
        }

        return productModel
    }

    suspend fun getProductsByCategoryFromApi(category:String):List<ProductCatalogModel>?{
        val productDTOList = productApi.getProductsByCategory(category)
        var productModelList:List<ProductCatalogModel>? = null


        if(!productDTOList.isNullOrEmpty()){
            productModelList = productDTOList.map { it.toProductCatalogModel() }
        }

        return productModelList
    }

    suspend fun getAllProductsByCategoryFromDb(category:String):List<ProductCatalogModel>?{
        val productEntityList = withContext(Dispatchers.IO){ productDao.getProductsWithRankingByCategory(category)}
        var productModelList:List<ProductCatalogModel>? = null

        if(!productEntityList.isNullOrEmpty()){
            productModelList = productEntityList.map { it.toProductCatalogModel() }
        }

        return productModelList
    }

    suspend fun insertProductToDb(productModel:ProductCatalogModel){
        val productEntity = productModel.toProductEntity()
        val ratingEntity = productModel.rating.toRatingEntity(productModel.productId)

        withContext(Dispatchers.IO){
            productDao.insertProductWithRating(productEntity,ratingEntity)
        }
    }

    suspend fun insertAllProductsToDb(productModelList:List<ProductCatalogModel>){
        val productEntityList = productModelList.map { it.toProductEntity() }
        val ratingEntityList = productModelList.map { it.rating.toRatingEntity(it.productId) }

        withContext(Dispatchers.IO){
            productDao.insertAllProductsWithRating(productEntityList,ratingEntityList)
        }
    }


    suspend fun updateProductFromDb(productModel:ProductCatalogModel){
        val productEntity = productModel.toProductEntity()
        val ratingEntity = productModel.rating.toRatingEntity(productModel.productId)

        withContext(Dispatchers.IO){
            productDao.updateProductWithRating(productEntity,ratingEntity)
        }
    }

    suspend fun updateAllProductsFromDb(productModelList:List<ProductCatalogModel>){
        val productEntityList = productModelList.map { it.toProductEntity() }
        val ratingEntityList = productModelList.map { it.rating.toRatingEntity(it.productId) }

         withContext(Dispatchers.IO) {
            productDao.updateAllProductsWithRating(productEntityList,ratingEntityList)
        }

    }

    suspend fun deleteProductFromDb(productModel:ProductCatalogModel){
        val productEntity = productModel.toProductEntity()
        val ratingEntity = productModel.rating.toRatingEntity(productModel.productId)

        withContext(Dispatchers.IO){
            productDao.deleteProductWithRating(productEntity,ratingEntity)
        }
    }

    suspend fun deleteAllProductsFromDb(productModelList:List<ProductCatalogModel>){
        val productEntityList = productModelList.map { it.toProductEntity() }
        val ratingEntityList = productModelList.map { it.rating.toRatingEntity(it.productId) }

        withContext(Dispatchers.IO){
            productDao.deleteAllProductsWithRating(productEntityList,ratingEntityList)
        }
    }
}