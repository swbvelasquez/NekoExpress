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
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.ui.view.fragment.DetailProductCatalogFragment
import com.swbvelasquez.nekoexpress.ui.view.fragment.ExposeCategoryFragment
import com.swbvelasquez.nekoexpress.ui.view.fragment.ExposeProductCatalogFragment
import com.swbvelasquez.nekoexpress.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var fragmentList:MutableList<Fragment>
    private lateinit var cart:CartModel
    private var currentFragment : Fragment? = null

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        showCategories()
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
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
        viewModel.getCart().observe(this){
            cart = it
        }
        viewModel.getLastAvailableCart(Constants.USER_ID)
    }

    private fun showCategories(){
        val fragment = ExposeCategoryFragment.newInstance()

        fragment.selectCategory{ category ->
            showProductsByCategory(category.toJson())
        }
        fragment.onBackPressed {
            finish()
        }

        fragmentList = mutableListOf()
        addFragment(fragment,ExposeCategoryFragment.TAG)
    }

    private fun showProductsByCategory(category:String){
        val fragment = ExposeProductCatalogFragment.newInstance(category)

        fragment.selectProduct { product ->
            showProductDetails(product.productId,cart.cartId,product.category)
        }
        fragment.onBackPressed {
            removeFragment(it)
        }

        addFragment(fragment,ExposeProductCatalogFragment.TAG)
    }

    private fun showProductDetails(productId:Long,cartId:Long,category:String){
        val fragment = DetailProductCatalogFragment.newInstance(productId,cartId,category)

        fragment.onBackPressed {
            removeFragment(it)
        }

        addFragment(fragment,DetailProductCatalogFragment.TAG)
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

            for(i in fragmentList.size - 1 downTo index+1){
                fragmentList.removeAt(i)
            }
            currentFragment = fragmentList.last()
            supportFragmentManager.popBackStackImmediate(destinyTag,0)
        }else{
            destinyFragment = supportFragmentManager.findFragmentByTag(ExposeCategoryFragment.TAG)

            if(destinyFragment!=null){
                fragmentList.clear()
                fragmentList.add(destinyFragment)
                supportFragmentManager.popBackStackImmediate(ExposeCategoryFragment.TAG,0)
            }
        }
    }
}