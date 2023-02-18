package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.model.ProductCatalogWithRatingDto
import com.swbvelasquez.nekoexpress.data.network.model.ProductCatalogDto

data class ProductCatalogModel(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating: RatingModel,
    var isFavorite:Boolean=false
)

fun ProductCatalogDto.toProductCatalogModel() = ProductCatalogModel(id=id,title=title,price=price,description=description,category=category,image=image,rating=RatingModel(rating.rate,rating.count))

fun ProductCatalogWithRatingDto.toProductCatalogModel() = ProductCatalogModel(id=productCatalog.id,title=productCatalog.title,price=productCatalog.price,description=productCatalog.description,
    category=productCatalog.category,image=productCatalog.image,rating=RatingModel(rating.rate,rating.count)
)