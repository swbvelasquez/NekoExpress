package com.swbvelasquez.nekoexpress.common

import com.swbvelasquez.nekoexpress.models.ProductModel
import com.swbvelasquez.nekoexpress.models.RatingModel

class ProductProvider {
    companion object{
        val productList:List<ProductModel> = listOf(
            ProductModel(1,"Teclado Micronics",35.50,"Teclado gamer para computadora","electronica"
                ,"https://http2.mlstatic.com/D_NQ_NP_944449-MLA50305077491_062022-O.jpg", RatingModel(3.4,120)),
            ProductModel(1,"Mouse Lumiere",60.50,"Mouse gamer para computadora","electronica"
                ,"https://compusystemperu.com/wp-content/uploads/2021/02/g360_3.jpg", RatingModel(4.3,450)),
            ProductModel(1,"Pantalla Gigabyte 27 pulgadas",235.99,"Pantalla gamer para computadora","electronica"
                ,"https://m.media-amazon.com/images/I/71Ef21RvypL._AC_SL1500_.jpg", RatingModel(3.3,320)),
            ProductModel(1,"Headseth Micronics",67.99,"Headseth gamer para computadora","electronica"
                ,"https://radioshackbo.com/wp-content/uploads/2019/10/CRIMSON-Audifonos-Gaming-para-PC-2606003-01.jpg", RatingModel(4.5,180))
        )
    }
}