package com.swbvelasquez.nekoexpress.domain.model

import com.swbvelasquez.nekoexpress.data.database.model.ProductWithRatingDto
import com.swbvelasquez.nekoexpress.data.network.model.ProductDto

data class ProductCatalogModel(
    val productId:Long,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating: RatingModel,
    var isFavorite:Boolean=false
)

fun ProductDto.toProductCatalogModel() = ProductCatalogModel(productId=productId,title=title,price=price,description=description,category=category,image=image,rating=RatingModel(rating.rate,rating.count))

fun ProductWithRatingDto.toProductCatalogModel() = ProductCatalogModel(productId=productCatalog.productId,title=productCatalog.title,price=productCatalog.price,description=productCatalog.description,
    category=productCatalog.category,image=productCatalog.image,rating=RatingModel(rating.rate,rating.count)
)