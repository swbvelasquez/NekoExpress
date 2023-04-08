package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.FragmentProductCatalogDetailBinding
import com.swbvelasquez.nekoexpress.domain.model.ProductCartModel
import com.swbvelasquez.nekoexpress.domain.model.ProductCatalogModel
import com.swbvelasquez.nekoexpress.domain.model.toProductCartModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.ProductCatalogDetailViewModel


private const val PRODUCT_ID = "PRODUCT_ID"
private const val PRODUCT_CATEGORY = "PRODUCT_CATEGORY"
private const val CART_ID = "CART_ID"

class ProductCatalogDetailFragment : Fragment() {
    companion object {
        val TAG:String = ProductCatalogDetailFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(productId: Long,cartId: Long,category:String) =
            ProductCatalogDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(PRODUCT_ID, productId)
                    putString(PRODUCT_CATEGORY, category)
                    putLong(CART_ID,cartId)
                }
            }
    }

    private lateinit var binding: FragmentProductCatalogDetailBinding
    private lateinit var productCatalog:ProductCatalogModel
    private var productId:Long=0
    private var cartId:Long=0
    private var category:String= ""
    private val viewModel: ProductCatalogDetailViewModel by viewModels()
    private var onClickBackPressed: ((String)->Unit)? = null
    private var selectedColor:String = ""
    private var selectedSize:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle->
            productId = bundle.getLong(PRODUCT_ID)
            category = bundle.getString(PRODUCT_CATEGORY,"")
            cartId = bundle.getLong(CART_ID)
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
        binding = FragmentProductCatalogDetailBinding.inflate(inflater,container,false)
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
        viewModel.getTypeException().observe(viewLifecycleOwner){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                activity?.let { Functions.showSimpleMessage(it, exception.typeException.message) }
            }
        }
        viewModel.getResult().observe(viewLifecycleOwner){ result ->
            when(result){
                is ProductCatalogModel -> {
                    productCatalog = result
                    setupUiProduct(result)
                }
                is ProductCartModel -> requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        viewModel.getProductDetails(productId)
    }

    private fun setupUiProduct(product:ProductCatalogModel){
        with(binding){
            tvTitle.text = product.title
            tvPrice.text = String.format(getString(R.string.format_item_product_price),product.price)
            tvScore.text = String.format(getString(R.string.format_item_product_score),product.rating.rate)
            tvReviewAmount.text = String.format(getString(R.string.format_item_product_review),product.rating.count)
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
            btnAddToCart.setOnClickListener {
                addProductToCart()
            }

            if(category.contains(Constants.CLOTH_CATEGORY)){
                selectedSize = tvSizeSmall.tag as String
                selectedColor = imvColorRed.tag as String

                lySizeSmall.setOnClickListener {
                    setActiveButtonSize(lySizeSmall,tvSizeSmall)
                    setInactiveButtonsSize(lySizeMedium,tvSizeMedium,lySizeLarge,tvSizeLarge,lySizeExtraLarge,tvSizeExtraLarge)
                }
                lySizeMedium.setOnClickListener {
                    setActiveButtonSize(lySizeMedium,tvSizeMedium)
                    setInactiveButtonsSize(lySizeSmall,tvSizeSmall,lySizeLarge,tvSizeLarge,lySizeExtraLarge,tvSizeExtraLarge)
                }
                lySizeLarge.setOnClickListener {
                    setActiveButtonSize(lySizeLarge,tvSizeLarge)
                    setInactiveButtonsSize(lySizeSmall,tvSizeSmall,lySizeMedium,tvSizeMedium,lySizeExtraLarge,tvSizeExtraLarge)
                }
                lySizeExtraLarge.setOnClickListener {
                    setActiveButtonSize(lySizeExtraLarge,tvSizeExtraLarge)
                    setInactiveButtonsSize(lySizeSmall,tvSizeSmall,lySizeMedium,tvSizeMedium,lySizeLarge,tvSizeLarge)
                }
                imvColorRed.setOnClickListener {
                    setActiveButtonColor(imvColorRed)
                    setInactiveButtonsColor(imvColorBlue,imvColorGreen,imvColorOrange,imvColorPurple)
                }
                imvColorBlue.setOnClickListener {
                    setActiveButtonColor(imvColorBlue)
                    setInactiveButtonsColor(imvColorRed,imvColorGreen,imvColorOrange,imvColorPurple)
                }
                imvColorGreen.setOnClickListener {
                    setActiveButtonColor(imvColorGreen)
                    setInactiveButtonsColor(imvColorBlue,imvColorRed,imvColorOrange,imvColorPurple)
                }
                imvColorOrange.setOnClickListener {
                    setActiveButtonColor(imvColorOrange)
                    setInactiveButtonsColor(imvColorBlue,imvColorGreen,imvColorRed,imvColorPurple)
                }
                imvColorPurple.setOnClickListener {
                    setActiveButtonColor(imvColorPurple)
                    setInactiveButtonsColor(imvColorBlue,imvColorGreen,imvColorOrange,imvColorRed)
                }
            }else{
                hideClothingOptions(tvTitleSize,tvTitleColor,lySizeSmall,lySizeMedium,lySizeLarge,lySizeExtraLarge,imvColorRed,imvColorBlue,imvColorGreen,imvColorOrange,imvColorPurple)
            }
        }
    }

    private fun hideClothingOptions(vararg views:View){
        views.forEach { it.visibility = View.GONE }
    }

    private fun setInactiveButtonsSize(vararg views:View){
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

    private fun setActiveButtonSize(layout:LinearLayout, textView: TextView){
        layout.setBackgroundResource(R.drawable.drw_size_layout_active)
        activity?.let { textView.setTextColor(it.getColor(R.color.color_on_primary)) }
        selectedSize = textView.tag as String
    }

    private fun setInactiveButtonsColor(vararg views:ImageView){
        for(view in views){
            view.setImageDrawable(null)
        }
    }

    private fun setActiveButtonColor(view:ImageView){
        view.setImageResource(R.drawable.ic_check)
        selectedColor = view.tag as String
    }

    private fun addProductToCart(){
        val productCart = productCatalog.toProductCartModel(0, cartId).apply {
            size=selectedSize
            color=selectedColor
        }
        viewModel.addProductToCart(productCart)
    }

    fun onBackPressed(onClickBackPressed:(String)->Unit){
        this.onClickBackPressed=onClickBackPressed
    }
}