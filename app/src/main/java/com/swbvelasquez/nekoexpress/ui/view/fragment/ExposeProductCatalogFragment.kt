package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.util.Functions.fromJson
import com.swbvelasquez.nekoexpress.databinding.FragmentExposeCategoryBinding
import com.swbvelasquez.nekoexpress.databinding.FragmentExposeProductCatalogBinding
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel

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
    }

}