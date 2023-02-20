package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel

@Entity(tableName = "CategoryTable")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "image") val image:Int)

fun CategoryModel.toCategoryEntity() = CategoryEntity(id=id,name=name,image=image)