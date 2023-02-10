package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Functions.showSimpleMessage
import com.swbvelasquez.nekoexpress.databinding.FragmentProductCatalogListBinding
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.domain.model.toProductCartModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.ProductCatalogAdapter
import com.swbvelasquez.nekoexpress.ui.viewmodel.ProductCatalogListFragmentViewModel


class ProductCatalogListFragment : Fragment() {
    companion object{
        val TAG: String = ProductCatalogListFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentProductCatalogListBinding
    private lateinit var productCartList:MutableList<ProductCartModel>
    private lateinit var productAdapter: ProductCatalogAdapter
    private lateinit var recyclerLayoutManager: LinearLayoutManager

    private val viewModel:ProductCatalogListFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductCatalogListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpViewModel()
    }

    private fun setUpRecyclerView(){
        recyclerLayoutManager = LinearLayoutManager(activity)
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
        viewModel.isLoading().observe(viewLifecycleOwner){ loading ->
            binding.lyProgressBar.pgLoading.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getProductCatalogList().observe(viewLifecycleOwner){ productList ->
            productAdapter.submitList(productList)
        }
        viewModel.getTypeException().observe(viewLifecycleOwner){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                activity?.let { showSimpleMessage(it,exception.typeException.message) }
            }
        }

        viewModel.getAllProducts()
    }
}