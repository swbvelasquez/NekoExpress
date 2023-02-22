package com.swbvelasquez.nekoexpress.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.ActivityMainBinding
import com.swbvelasquez.nekoexpress.ui.view.fragment.ExposeCategoryFragment
import com.swbvelasquez.nekoexpress.ui.view.fragment.ManageProductListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startProductCatalogListFragment()
    }

    private fun startProductCatalogListFragment(){
        val fragment = ExposeCategoryFragment.newInstance()
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragment.sendCategory{
            Functions.showSimpleMessage(this,"Categoria seleccionada ${it.name}")
        }

        fragmentTransaction.add(R.id.lyMainContent,fragment,ExposeCategoryFragment.TAG)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}