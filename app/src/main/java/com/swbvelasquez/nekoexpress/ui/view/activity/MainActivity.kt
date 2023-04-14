package com.swbvelasquez.nekoexpress.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.core.util.Functions.toJson
import com.swbvelasquez.nekoexpress.databinding.ActivityMainBinding
import com.swbvelasquez.nekoexpress.ui.view.fragment.*
import com.swbvelasquez.nekoexpress.ui.viewmodel.MainViewModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private var fragmentList:MutableList<Fragment> = mutableListOf()
    private var currentFragment : Fragment? = null
    private var baseTag : String = ""
    private var cartId : Long = 0
    private val userId : Long = Constants.DEFAULT_USER_ID

    private val viewModel : MainViewModel by viewModels {
        MainViewModelFactory(userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()
        setupViewModel()
        showCategories()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getLastAvailableCart()
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
    }

    private fun setupBottomNav(){
        binding.bnvMain.background = null
        binding.bnvMain.setOnItemSelectedListener { option ->

            when(option.itemId){
                R.id.optHome -> {
                    if(currentFragment !is ExposeCategoryFragment) {
                        removeAllFragments()
                        showCategories()
                    }
                }
                R.id.optUserProfile -> {
                    if(currentFragment !is UserProfileFragment) {
                        removeAllFragments()
                        showUserProfile()
                    }
                }
                R.id.optFavoriteProducts -> {
                    if(currentFragment !is ExposeFavoriteProductFragment) {
                        removeAllFragments()
                        showFavoriteProducts()
                    }
                }
                R.id.optSalesHistory -> {
                    if(currentFragment !is ExposeSaleHistoryFragment) {
                        removeAllFragments()
                        showSalesHistory()
                    }
                }
            }

            true
        }
        binding.fabCheckoutCart.setOnClickListener {
            showCart()
        }
    }

    private fun setupViewModel(){
        viewModel.isLoading().observe(this){ loading ->
            binding.lyProgressBar.root.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getTypeException().observe(this){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                Functions.showSimpleMessage(this, exception.typeException.message)
            }
        }
        viewModel.getCartId().observe(this){
            cartId = it
        }
        viewModel.totalQuantity.observe(this){
            binding.tvTotalQuantityProduct.text = it.toString()
        }
    }

    private fun showCategories(){
        val fragment = ExposeCategoryFragment.newInstance()

        fragment.selectCategory{ category ->
            showProductsByCategory(category.toJson())
        }
        fragment.onBackPressed {
            finish()
        }

        addFragment(fragment,ExposeCategoryFragment.TAG)
        baseTag = ExposeCategoryFragment.TAG
    }

    private fun showProductsByCategory(category:String){
        val fragment = ExposeProductCatalogFragment.newInstance(category)

        fragment.selectProduct { product ->
            showProductDetails(product.productId,cartId,product.category)
        }
        fragment.onBackPressed { destiny->
            removeFragment(destiny)
        }

        addFragment(fragment,ExposeProductCatalogFragment.TAG)
    }

    private fun showProductDetails(productId:Long,cartId:Long,category:String){
        val fragment = ProductCatalogDetailFragment.newInstance(productId,cartId,category)

        fragment.onBackPressed { destiny->
            removeFragment(destiny)
        }

        addFragment(fragment,ProductCatalogDetailFragment.TAG)
    }

    private fun showCart(){
        if(currentFragment !is CheckoutCartFragment && currentFragment !is PaymentDetailFragment) {
            val fragment = CheckoutCartFragment.newInstance(cartId)

            fragment.onBackPressed {
                removeFragment(baseTag)
            }
            fragment.onPayOrder { totalOrder ->
                showPaymentDetails(cartId,totalOrder)
            }

            addFragment(fragment, CheckoutCartFragment.TAG)
        }
    }

    private fun showPaymentDetails(cartId:Long,total:Double){
        val fragment = PaymentDetailFragment.newInstance(cartId,total)

        fragment.onBackPressed { destiny->
            removeFragment(destiny)
        }

        fragment.onConfirmOrder {
            removeFragment(baseTag)
            viewModel.getLastAvailableCart()
        }

        addFragment(fragment,PaymentDetailFragment.TAG)
    }

    private fun showUserProfile(){
        Functions.showSimpleMessage(this,"User Profile")
        baseTag = UserProfileFragment::class.java.simpleName
    }

    private fun showFavoriteProducts(){
        Functions.showSimpleMessage(this,"Favorite Products")
        baseTag = ExposeFavoriteProductFragment::class.java.simpleName
    }

    private fun showSalesHistory(){
        val fragment = ExposeSaleHistoryFragment.newInstance(userId)

        fragment.selectInvoice{ invoice ->
            showInvoiceDetails(invoice.invoiceId)
        }
        fragment.onBackPressed {
            finish()
        }

        addFragment(fragment,ExposeSaleHistoryFragment.TAG)
        baseTag = ExposeSaleHistoryFragment::class.java.simpleName
    }

    private fun showInvoiceDetails(invoiceId:Long){
        val fragment = InvoiceDetailFragment.newInstance(invoiceId)

        fragment.onBackPressed { destiny->
            removeFragment(destiny)
        }

        addFragment(fragment,InvoiceDetailFragment.TAG)
    }

    private fun addFragment(fragment:Fragment,tag:String){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.lyMainContent,fragment,tag)
        fragmentTransaction.addToBackStack(tag)
        currentFragment?.let {
            fragmentTransaction.hide(it)
        }

        fragmentList.add(fragment)
        currentFragment = fragment

        fragmentTransaction.commit()
    }

    private fun removeFragment(destinyTag:String){
        var destinyFragment = supportFragmentManager.findFragmentByTag(destinyTag)

        if(destinyFragment!=null){
            val index = fragmentList.indexOf(destinyFragment)

            for(i in fragmentList.size - 1 downTo index + 1){
                fragmentList.removeAt(i)
            }
            currentFragment = fragmentList.last()
            supportFragmentManager.popBackStackImmediate(destinyTag,0)
        }else{
            destinyFragment = supportFragmentManager.findFragmentByTag(baseTag)

            if(destinyFragment!=null){
                fragmentList.clear()
                fragmentList.add(destinyFragment)
                currentFragment = destinyFragment
                supportFragmentManager.popBackStackImmediate(baseTag,0)
            }else{
                removeAllFragments()
                showCategories()
                binding.bnvMain.selectedItemId = R.id.optHome
            }
        }
    }

    private fun removeAllFragments(){
        while (supportFragmentManager.backStackEntryCount >0){
            supportFragmentManager.popBackStackImmediate()
        }

        fragmentList.clear()
        currentFragment = null
    }
}