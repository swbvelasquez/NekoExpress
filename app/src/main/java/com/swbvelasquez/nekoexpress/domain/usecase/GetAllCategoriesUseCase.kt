package com.swbvelasquez.nekoexpress.domain.usecase

import com.swbvelasquez.nekoexpress.data.repository.CategoryRepository
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel

class GetAllCategoriesUseCase {
    private val repository = CategoryRepository()

    suspend operator fun invoke():List<CategoryModel>?{
        var categoryList = repository.getAllCategoriesFromDb()

        if(categoryList.isNullOrEmpty()){
            categoryList = repository.getAllCategoriesFromApi()

            if(!categoryList.isNullOrEmpty()){
                repository.insertAllCategoriesToDb(categoryList)
            }
        }

        return categoryList
    }
}