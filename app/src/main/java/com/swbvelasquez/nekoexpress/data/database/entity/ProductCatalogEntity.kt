package com.swbvelasquez.nekoexpress.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductCatalogTable")
data class ProductCatalogEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "price") val price:Double,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "image") val image:String)