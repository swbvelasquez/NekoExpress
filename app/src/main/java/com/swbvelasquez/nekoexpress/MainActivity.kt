package com.swbvelasquez.nekoexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.swbvelasquez.nekoexpress.adapters.ProductCatalogAdapter
import com.swbvelasquez.nekoexpress.commons.ProductCatalogProvider
import com.swbvelasquez.nekoexpress.databinding.ActivityMainBinding
import com.swbvelasquez.nekoexpress.models.ProductCartModel
import com.swbvelasquez.nekoexpress.models.toProductCartModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productCartList:MutableList<ProductCartModel>
    private lateinit var productAdapter:ProductCatalogAdapter
    private lateinit var recyclerLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        recyclerLayoutManager = LinearLayoutManager(this)
        productCartList = mutableListOf()

        productAdapter = ProductCatalogAdapter{ product ->
            val productCart = product.toProductCartModel()
            productCartList.add(productCart)
        }.also {
            it.submitList(ProductCatalogProvider.productList.toMutableList())
        }

        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = recyclerLayoutManager
            hasFixedSize()
        }
    }
}