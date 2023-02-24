package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.core.util.Functions.fromJson
import com.swbvelasquez.nekoexpress.databinding.FragmentExposeCategoryBinding
import com.swbvelasquez.nekoexpress.databinding.FragmentExposeProductCatalogBinding
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.ExposeCategoryAdapter
import com.swbvelasquez.nekoexpress.ui.view.adapter.ExposeProductCatalogAdapter
import com.swbvelasquez.nekoexpress.ui.viewmodel.ExposeProductCatalogViewModel

private const val CATEGORY_PARAM = "CATEGORY_PARAM"

class ExposeProductCatalogFragment : Fragment() {
    companion object {
        val TAG:String = ExposeProductCatalogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(categoryModel: String) =
            ExposeProductCatalogFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_PARAM, categoryModel)
                }
            }
    }

    private lateinit var binding: FragmentExposeProductCatalogBinding
    private lateinit var categoryModel: CategoryModel
    private lateinit var productAdapter: ExposeProductCatalogAdapter
    private val viewModel : ExposeProductCatalogViewModel by viewModels()
    private var onClickProductCatalog : ((ProductCatalogModel)->Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            val categoryJson = bundle.getString(CATEGORY_PARAM)

            categoryJson?.let {
                categoryModel = it.fromJson()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExposeProductCatalogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView(){
        productAdapter = ExposeProductCatalogAdapter(
            onClickListener = { product ->
                activity?.let { Functions.showSimpleMessage(it,"Producto Seleccionado: ${product.title}") }
                onClickProductCatalog?.invoke(product)
            },
            onClickFavoriteListener = { product ->
                activity?.let { Functions.showSimpleMessage(it,"Producto Favorito: ${product.title}") }
            }
        )

        binding.rvProduct.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(activity,2)
            setHasFixedSize(true)
        }
    }

    private fun setupViewModel(){
        viewModel.isLoading().observe(viewLifecycleOwner){ loading ->
            binding.lyProgressBar.pgLoading.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getProductList().observe(viewLifecycleOwner){ productList ->
            productAdapter.submitList(productList)
        }
        viewModel.getTypeException().observe(viewLifecycleOwner){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                activity?.let { Functions.showSimpleMessage(it, exception.typeException.message) }
            }
        }

        viewModel.getProductsByCategory(categoryModel.name)
    }

    fun selectProduct(onClickProductCatalog:(ProductCatalogModel)->Unit){
        this.onClickProductCatalog = onClickProductCatalog
    }
}