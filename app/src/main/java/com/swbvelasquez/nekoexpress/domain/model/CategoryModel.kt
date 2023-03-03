package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.CategoryEntity
import com.swbvelasquez.nekoexpress.data.network.model.CategoryDto

data class CategoryModel(
    val categoryId:Long,
    val name:String,
    val image:String
)

fun CategoryDto.toCategoryModel() = CategoryModel(categoryId=categoryId,name=name,image=image)

fun CategoryEntity.toCategoryModel() = CategoryModel(categoryId=categoryId,name=name,image=image)