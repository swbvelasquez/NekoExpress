package com.swbvelasquez.nekoexpress.data.repository

import androidx.room.Transaction
import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Constants
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
        var productModelList:List<ProductCatalogModel>? = null


        if(!productDTOList.isNullOrEmpty()){
            productModelList = productDTOList.map { it.toProductCatalogModel() }
        }

        return productModelList
    }

    suspend fun getAllProductsFromDatabase():List<ProductCatalogModel>?{
        val productEntityList = withContext(Dispatchers.IO){ productDao.getAllProductsWithRanking() }
        var productModelList:List<ProductCatalogModel>? = null

        if(!productEntityList.isNullOrEmpty()){
            productModelList = productEntityList.map { it.toProductCatalogModel() }
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

    suspend fun getProductByIdFromDb(id:Int):ProductCatalogModel?{
        val productEntity = withContext(Dispatchers.IO){ productDao.getProductWithRankingById(id) }
        var productModel:ProductCatalogModel? = null

        if(productEntity !=null){
            productModel = productEntity.toProductCatalogModel()
        }

        return productModel
    }

    @Transaction
    suspend fun insertProductToDb(productModel:ProductCatalogModel){
        val productEntity = productModel.toProductCatalogEntity()
        val ratingEntity = productModel.rating.toRatingEntity(productModel.id)

        val result =  withContext(Dispatchers.IO){
            productDao.insertProduct(productEntity)>0 && ratingDao.insertRating(ratingEntity)>0
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_ONE)
    }

    @Transaction
    suspend fun insertAllProductsToDb(productModelList:List<ProductCatalogModel>){
        val productEntityList = productModelList.map { it.toProductCatalogEntity() }
        val ratingEntityList = productModelList.map { it.rating.toRatingEntity(it.id) }

        val result = withContext(Dispatchers.IO) {
            val resultProductList = productDao.insertAllProducts(productEntityList) //si es un insert retorna los ids si es auto generado y retorna 1 si es generado manualmente, cuando falla retorna -1
            val resultRatingList = ratingDao.insertAllRatings(ratingEntityList)

            return@withContext !(resultProductList.isEmpty() || resultProductList.contains(-1) || resultRatingList.isEmpty() || resultRatingList.contains(-1))
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_LIST)
    }

    @Transaction
    suspend fun updateProductFromDb(productModel:ProductCatalogModel){
        val productEntity = productModel.toProductCatalogEntity()
        val ratingEntity = productModel.rating.toRatingEntity(productModel.id)

        val result =  withContext(Dispatchers.IO){
            productDao.updateProduct(productEntity)>0 && ratingDao.updateRating(ratingEntity)>0
        }

        if(!result) throw CustomException(CustomTypeException.DB_UPDATE_ONE)
    }

    @Transaction
    suspend fun updateAllProductsFromDb(productModelList:List<ProductCatalogModel>){
        val productEntityList = productModelList.map { it.toProductCatalogEntity() }
        val ratingEntityList = productModelList.map { it.rating.toRatingEntity(it.id) }

        val result = withContext(Dispatchers.IO) {
            val resultProductList = productDao.updateAllProducts(productEntityList) //si es un update o delete, retorna la cantidad de filas afectadas o -1 si fallo
            val resultRatingList = ratingDao.updateAllRatings(ratingEntityList)

            return@withContext resultProductList==productEntityList.size && resultRatingList==ratingEntityList.size
        }

        if(!result) throw CustomException(CustomTypeException.DB_UPDATE_LIST)
    }

    @Transaction
    suspend fun deleteProductFromDb(productModel:ProductCatalogModel){
        val productEntity = productModel.toProductCatalogEntity()
        val ratingEntity = productModel.rating.toRatingEntity(productModel.id)

        val result =  withContext(Dispatchers.IO){
            productDao.deleteProduct(productEntity)>0 && ratingDao.deleteRating(ratingEntity)>0
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_ONE)
    }

    @Transaction
    suspend fun deleteAllProductsFromDb(productModelList:List<ProductCatalogModel>){
        val productEntityList = productModelList.map { it.toProductCatalogEntity() }
        val ratingEntityList = productModelList.map { it.rating.toRatingEntity(it.id) }

        val result = withContext(Dispatchers.IO) {
            val resultProductList = productDao.deleteAllProducts(productEntityList) //si es un update o delete, retorna la cantidad de filas afectadas o -1 si fallo
            val resultRatingList = ratingDao.deleteAllRatings(ratingEntityList)

            return@withContext resultProductList!=Constants.DB_OPERATION_FAIL && resultRatingList!=Constants.DB_OPERATION_FAIL
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_LIST)
    }
}