package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.entity.CategoryEntity
import com.swbvelasquez.nekoexpress.data.network.model.CategoryDto

data class CategoryModel(
    val id:Int,
    val name:String,
    val image:Int
)

fun CategoryDto.toCategoryModel() = CategoryModel(id=id,name=name,image=image)

fun CategoryEntity.toCategoryModel() = CategoryModel(id=id,name=name,image=image)