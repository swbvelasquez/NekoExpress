package com.swbvelasquez.nekoexpress.data.repository

import com.swbvelasquez.nekoexpress.NekoApplication
import com.swbvelasquez.nekoexpress.core.error.CustomException
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.data.database.entity.toCategoryEntity
import com.swbvelasquez.nekoexpress.data.network.service.CategoryService
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.domain.model.toCategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository {
    private val categoryApi = CategoryService()
    private val categoryDao = NekoApplication.database.getCategoryDao()

    suspend fun getAllCategoriesFromApi():List<CategoryModel>?{
        val categoryDtoList = categoryApi.getAllCategories()?.mapIndexed { index, name -> Functions.getCompleteCategoryDto(index,name) }
        var categoryModelList: List<CategoryModel>? = null

        if(!categoryDtoList.isNullOrEmpty()){
            categoryModelList = categoryDtoList.map { it.toCategoryModel() }
        }

        return categoryModelList
    }

    suspend fun getAllCategoriesFromDb():List<CategoryModel>?{
        val categoryEntityList = withContext(Dispatchers.IO) { categoryDao.getAllCategories() }
        var categoryModelList: List<CategoryModel>? = null

        if(!categoryEntityList.isNullOrEmpty()){
            categoryModelList = categoryEntityList.map { it.toCategoryModel() }
        }

        return categoryModelList
    }

    suspend fun insertCategoryToDb(categoryModel:CategoryModel){
        val categoryEntity = categoryModel.toCategoryEntity()

        val result = withContext(Dispatchers.IO){
            categoryDao.insertCategory(categoryEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_ONE)
    }

    suspend fun insertAllCategoriesToDb(categoryModelList:List<CategoryModel>){
        val categoryEntityList = categoryModelList.map { it.toCategoryEntity() }

        val result = withContext(Dispatchers.IO){
            val resultCategoryList =  categoryDao.insertAllCategories(categoryEntityList)

            return@withContext !(resultCategoryList.isEmpty() || resultCategoryList.contains(-1))
        }

        if(!result) throw CustomException(CustomTypeException.DB_INSERT_LIST)
    }

    suspend fun deleteCategoryToDb(categoryModel:CategoryModel){
        val categoryEntity = categoryModel.toCategoryEntity()

        val result = withContext(Dispatchers.IO){
            categoryDao.deleteCategory(categoryEntity) > 0
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_ONE)
    }

    suspend fun deleteAllCategoriesToDb(categoryModelList:List<CategoryModel>){
        val categoryEntityList = categoryModelList.map { it.toCategoryEntity() }

        val result = withContext(Dispatchers.IO){
            val resultCategoryList =  categoryDao.deleteAllCategories(categoryEntityList)

            return@withContext resultCategoryList != Constants.DB_OPERATION_FAIL
        }

        if(!result) throw CustomException(CustomTypeException.DB_DELETE_LIST)
    }
}