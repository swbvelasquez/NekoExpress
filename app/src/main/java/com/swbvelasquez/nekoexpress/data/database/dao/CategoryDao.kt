package com.swbvelasquez.nekoexpress.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.swbvelasquez.nekoexpress.data.database.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("select * from CategoryTable")
    suspend fun getAllCategories():List<CategoryEntity>?

    @Insert
    suspend fun insertCategory(category:CategoryEntity):Long

    @Transaction
    @Insert
    suspend fun insertCategoryList(categoryList:List<CategoryEntity>):List<Long>

    @Delete
    suspend fun deleteCategory(category:CategoryEntity):Int

    @Transaction
    @Delete
    suspend fun deleteCategoryList(categoryList:List<CategoryEntity>):Int
}