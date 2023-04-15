package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.core.util.Functions.fromJson
import com.swbvelasquez.nekoexpress.databinding.FragmentExposeProductCatalogBinding
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.domain.model.FavoriteProductModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.ExposeProductCatalogAdapter
import com.swbvelasquez.nekoexpress.ui.viewmodel.ExposeProductCatalogViewModel

private const val CATEGORY_PARAM = "CATEGORY_PARAM"
private const val USER_ID_PARAM = "USER_ID_PARAM"

class ExposeProductCatalogFragment : Fragment() {
    companion object {
        val TAG:String = ExposeProductCatalogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(userId: Long,categoryModel: String) =
            ExposeProductCatalogFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_PARAM, categoryModel)
                    putLong(USER_ID_PARAM, userId)
                }
            }
    }

    private lateinit var binding: FragmentExposeProductCatalogBinding
    private lateinit var categoryModel: CategoryModel
    private lateinit var productAdapter: ExposeProductCatalogAdapter
    private val viewModel : ExposeProductCatalogViewModel by viewModels()
    private var userId: Long = 0
    private var onClickProductCatalog : ((ProductCatalogModel)->Unit)? = null
    private var onClickBackPressed : ((String)->Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            val categoryJson = bundle.getString(CATEGORY_PARAM)

            categoryJson?.let {
                categoryModel = it.fromJson()
            }

            userId = bundle.getLong(USER_ID_PARAM,0)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG,"onBackPressed")
                    onClickBackPressed?.invoke(ExposeCategoryFragment.TAG)
                    remove()
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
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
                onClickProductCatalog?.invoke(product)
            },
            onClickFavoriteListener = { product ->
                if(product.isFavorite)
                    viewModel.addProductToFavorites(FavoriteProductModel(Constants.DEFAULT_USER_ID,product.productId))
                else
                    viewModel.deleteProductToFavorites(FavoriteProductModel(Constants.DEFAULT_USER_ID,product.productId))
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
            binding.lyProgressBar.root.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getProductList().observe(viewLifecycleOwner){ productList ->
            productAdapter.submitList(productList.toList())
        }
        viewModel.getTypeException().observe(viewLifecycleOwner){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                activity?.let { Functions.showSimpleMessage(it, exception.typeException.message) }
            }
        }

        viewModel.getProductsByCategory(userId,categoryModel.name)
    }

    fun selectProduct(onClickProductCatalog:(ProductCatalogModel)->Unit){
        this.onClickProductCatalog = onClickProductCatalog
    }

    fun onBackPressed(onClickBackPressed:(String)->Unit){
        this.onClickBackPressed=onClickBackPressed
    }
}