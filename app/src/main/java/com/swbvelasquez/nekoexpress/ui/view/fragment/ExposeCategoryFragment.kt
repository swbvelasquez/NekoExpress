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
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.FragmentExposeCategoryBinding
import com.swbvelasquez.nekoexpress.domain.model.CategoryModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.ExposeCategoryAdapter
import com.swbvelasquez.nekoexpress.ui.viewmodel.ExposeCategoryViewModel

class ExposeCategoryFragment : Fragment() {
    companion object {
        val TAG:String = ExposeCategoryFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = ExposeCategoryFragment()
    }

    private lateinit var binding: FragmentExposeCategoryBinding
    private lateinit var categoryAdapter: ExposeCategoryAdapter
    private val viewModel:ExposeCategoryViewModel by viewModels()
    private var onClickCategory:((CategoryModel)->Unit)? = null
    private var onClickBackPressed : (()->Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG,"onBackPressed")
                    onClickBackPressed?.invoke()
                    remove()
                }
            }
        )

        Log.d(TAG,"onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentExposeCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView(){
        categoryAdapter = ExposeCategoryAdapter { category ->
            onClickCategory?.invoke(category)
        }

        binding.rvCategory.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(activity,2)
            setHasFixedSize(true)
        }
    }

    private fun setupViewModel(){
        viewModel.isLoading().observe(viewLifecycleOwner){ loading ->
            binding.lyProgressBar.root.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getCategoryList().observe(viewLifecycleOwner){ categoryList ->
            categoryAdapter.submitList(categoryList.toList())
        }
        viewModel.getTypeException().observe(viewLifecycleOwner){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                activity?.let { Functions.showSimpleMessage(it, exception.typeException.message) }
            }
        }

        viewModel.getAllCategories()
    }

    fun selectCategory(onClickCategory:(CategoryModel)->Unit){
        this.onClickCategory = onClickCategory
    }

    fun onBackPressed(onClickBackPressed:()->Unit){
        this.onClickBackPressed=onClickBackPressed
    }
}