package com.swbvelasquez.nekoexpress.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.core.util.Functions.toJson
import com.swbvelasquez.nekoexpress.databinding.ActivityMainBinding
import com.swbvelasquez.nekoexpress.ui.view.fragment.ExposeCategoryFragment
import com.swbvelasquez.nekoexpress.ui.view.fragment.ExposeProductCatalogFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var currentFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupOnBackPressed()
        showCategories()
    }

    override fun onDestroy() {
        super.onDestroy()

        onBackPressedCallback.remove()
    }

    private fun setupOnBackPressed(){
        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                removeFragment()
            }
        }

        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun showCategories(){
        val fragment = ExposeCategoryFragment.newInstance()

        fragment.selectCategory{ category ->
            Functions.showSimpleMessage(this,"Categoria seleccionada ${category.name}")
            showProductsByCategory(category.toJson())
        }

        addFragment(fragment,ExposeCategoryFragment.TAG)
    }

    private fun showProductsByCategory(category:String){
        val fragment = ExposeProductCatalogFragment.newInstance(category)

        fragment.selectProduct { product ->
            Functions.showSimpleMessage(this,"Producto seleccionado ${product.title}")
        }

        addFragment(fragment,ExposeProductCatalogFragment.TAG)
    }

    private fun addFragment(fragment:Fragment,tag:String){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.lyMainContent,fragment,tag)

        if(fragment !is ExposeCategoryFragment) {
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.hide(currentFragment)
        }

        currentFragment = fragment
        fragmentTransaction.commit()
    }

    private fun removeFragment(){
        if(currentFragment !is ExposeCategoryFragment){
            supportFragmentManager.popBackStack()
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }
}