package com.swbvelasquez.nekoexpress.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.ui.view.adapter.ProductCatalogAdapter
import com.swbvelasquez.nekoexpress.core.provider.ProductCatalogProvider
import com.swbvelasquez.nekoexpress.databinding.ActivityMainBinding
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.domain.model.toProductCartModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productCartList:MutableList<ProductCartModel>
    private lateinit var productAdapter: ProductCatalogAdapter
    private lateinit var recyclerLayoutManager: LinearLayoutManager

    private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        setUpViewModel()
    }

    override fun onDestroy() {
        viewModel.isLoading().removeObservers(this)
        viewModel.getProductCatalogList().removeObservers(this)
        super.onDestroy()
    }

    private fun setUpRecyclerView(){
        recyclerLayoutManager = LinearLayoutManager(this)
        productCartList = mutableListOf()

        productAdapter = ProductCatalogAdapter{ product ->
            val productCart = product.toProductCartModel()
            productCartList.add(productCart)
        }

        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = recyclerLayoutManager
            hasFixedSize()
        }
    }

    private fun setUpViewModel(){
        viewModel.isLoading().observe(this){ loading ->
            binding.pgLoading.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getProductCatalogList().observe(this){ productList ->
            productAdapter.submitList(productList)
        }
        viewModel.getTypeException().observe(this){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getAllProducts()
    }
}