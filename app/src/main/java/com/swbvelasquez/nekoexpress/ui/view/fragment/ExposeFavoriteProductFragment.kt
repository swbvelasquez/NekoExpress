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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.FragmentExposeFavoriteProductBinding
import com.swbvelasquez.nekoexpress.domain.model.*
import com.swbvelasquez.nekoexpress.ui.view.adapter.ExposeFavoriteProductAdapter
import com.swbvelasquez.nekoexpress.ui.viewmodel.*

private const val USER_ID_PARAM = "USER_ID_PARAM"

class ExposeFavoriteProductFragment : Fragment() {
    companion object {
        val TAG:String = ExposeFavoriteProductFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(userId: Long) =
            ExposeFavoriteProductFragment().apply {
                arguments = Bundle().apply {
                    putLong(USER_ID_PARAM, userId)
                }
            }
    }

    private lateinit var binding: FragmentExposeFavoriteProductBinding
    private lateinit var favoriteProductAdapter: ExposeFavoriteProductAdapter
    private var userId: Long = 0
    private var onClickProductCatalog : ((ProductCatalogModel)->Unit)? = null
    private var onClickBackPressed: (()->Unit)? = null

    private val viewModel : ExposeFavoriteProductViewModel by viewModels {
        ExposeFavoriteProductViewModelFactory(userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            userId = bundle.getLong(USER_ID_PARAM,0)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG,"onBackPressed")
                    onClickBackPressed?.invoke()
                    remove()
                }
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExposeFavoriteProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView(){
        favoriteProductAdapter = ExposeFavoriteProductAdapter(
            onClickListener = { product ->
                onClickProductCatalog?.invoke(product)
            },
            onClickFavoriteListener = { product ->
                activity?.let { context ->
                    MaterialAlertDialogBuilder(context)
                        .setTitle(resources.getString(R.string.dialog_delete_item_title))
                        .setMessage(resources.getString(R.string.dialog_delete_item_message))
                        .setPositiveButton(resources.getString(R.string.dialog_delete_item_positive_button)) { _, _ ->
                            viewModel.deleteProductToFavorites(FavoriteProductModel(Constants.DEFAULT_USER_ID,product.productId))
                        }
                        .setNegativeButton(resources.getString(R.string.dialog_delete_item_negative_button)) { _, _ ->
                        }
                        .setCancelable(true)
                        .show()
                }
            }
        )

        binding.rvProduct.apply {
            adapter = favoriteProductAdapter
            layoutManager = GridLayoutManager(activity,2)
            setHasFixedSize(true)
        }
    }

    private fun setupViewModel(){
        viewModel.isLoading().observe(viewLifecycleOwner){ loading ->
            binding.lyProgressBar.root.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getTypeException().observe(viewLifecycleOwner){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                activity?.let { Functions.showSimpleMessage(it, exception.typeException.message) }
            }
        }
        viewModel.favoriteProductList.observe(viewLifecycleOwner){ favoriteProductList ->
            favoriteProductAdapter.submitList(favoriteProductList.toList())
        }
    }

    fun selectProduct(onClickProductCatalog:(ProductCatalogModel)->Unit){
        this.onClickProductCatalog = onClickProductCatalog
    }

    fun onBackPressed(onClickBackPressed:()->Unit){
        this.onClickBackPressed=onClickBackPressed
    }
}