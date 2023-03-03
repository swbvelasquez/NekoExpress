package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.util.Functions.fromJson
import com.swbvelasquez.nekoexpress.databinding.FragmentDetailProductCatalogBinding
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.DetailProductCatalogViewModel


private const val PRODUCT_PARAM = "PRODUCT_PARAM"

class DetailProductCatalogFragment : Fragment() {
    companion object {
        val TAG:String = DetailProductCatalogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(productCatalogModel: String) =
            DetailProductCatalogFragment().apply {
                arguments = Bundle().apply {
                    putString(PRODUCT_PARAM, productCatalogModel)
                }
            }
    }

    private lateinit var binding: FragmentDetailProductCatalogBinding
    private lateinit var productModel: ProductCatalogModel
    private val viewModel : DetailProductCatalogViewModel by viewModels()
    private var onClickBackPressed : ((String)->Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle->
            val productJson = bundle.getString(PRODUCT_PARAM)

            productJson?.let {
                productModel = it.fromJson()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG,"onBackPressed")
                    onClickBackPressed?.invoke(ExposeProductCatalogFragment.TAG)
                    remove()
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailProductCatalogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupButtons()
    }

    private fun setupViewModel(){
        viewModel.isLoading().observe(viewLifecycleOwner){ loading ->
            if(loading){
                binding.lyProgressBar.root.visibility =  View.VISIBLE
                binding.cvDetails.visibility = View.GONE
            }else{
                binding.lyProgressBar.root.visibility =  View.GONE
                binding.cvDetails.visibility = View.VISIBLE
            }
        }
        viewModel.getProduct().observe(viewLifecycleOwner){ product ->
            if(product.productId > 0) setupUiProduct(product)
        }

        viewModel.setProduct(productModel)
    }

    private fun setupUiProduct(product:ProductCatalogModel){
        with(binding){
            tvTitle.text = product.title
            tvPrice.text = String.format(getString(R.string.format_product_price),product.price)
            tvScore.text = String.format(getString(R.string.format_product_score),product.rating.rate)
            tvReviewAmount.text = String.format(getString(R.string.format_product_review),product.rating.count)
            tvDescription.text = product.description

            activity?.let {
                Glide
                    .with(it)
                    .load(product.image)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imvThumbnail)
            }
        }
    }

    private fun setupButtons(){
        with(binding){
            lySizeSmall.setOnClickListener {
                enableButtonSize(lySizeSmall,tvSizeSmall)
                disableButtonsSize(lySizeMedium,tvSizeMedium,lySizeLarge,tvSizeLarge,lySizeExtraLarge,tvSizeExtraLarge)
            }
            lySizeMedium.setOnClickListener {
                enableButtonSize(lySizeMedium,tvSizeMedium)
                disableButtonsSize(lySizeSmall,tvSizeSmall,lySizeLarge,tvSizeLarge,lySizeExtraLarge,tvSizeExtraLarge)
            }
            lySizeLarge.setOnClickListener {
                enableButtonSize(lySizeLarge,tvSizeLarge)
                disableButtonsSize(lySizeSmall,tvSizeSmall,lySizeMedium,tvSizeMedium,lySizeExtraLarge,tvSizeExtraLarge)
            }
            lySizeExtraLarge.setOnClickListener {
                enableButtonSize(lySizeExtraLarge,tvSizeExtraLarge)
                disableButtonsSize(lySizeSmall,tvSizeSmall,lySizeMedium,tvSizeMedium,lySizeLarge,tvSizeLarge)
            }
        }
    }

    private fun disableButtonsSize(vararg views:View){
        var layout:LinearLayout
        var textView:TextView

        for(view in views){
            if(view is LinearLayout){
                layout = view
                layout.setBackgroundResource(R.drawable.drw_size_layout_inactive)
            }else{
                textView = view as TextView
                activity?.let { textView.setTextColor(it.getColor(R.color.color_secondary_variant)) }
            }
        }
    }

    private fun enableButtonSize(layout:LinearLayout,textView: TextView){
        layout.setBackgroundResource(R.drawable.drw_size_layout_active)
        activity?.let { textView.setTextColor(it.getColor(R.color.color_on_primary)) }
    }

    fun onBackPressed(onClickBackPressed:(String)->Unit){
        this.onClickBackPressed=onClickBackPressed
    }
}