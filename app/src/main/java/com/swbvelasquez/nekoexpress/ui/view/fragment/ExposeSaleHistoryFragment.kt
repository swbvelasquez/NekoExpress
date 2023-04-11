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
import androidx.recyclerview.widget.LinearLayoutManager
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.FragmentExposeSaleHistoryBinding
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.ExposeProductCatalogAdapter
import com.swbvelasquez.nekoexpress.ui.view.adapter.ExposeSaleHistoryAdapter
import com.swbvelasquez.nekoexpress.ui.viewmodel.ExposeSaleHistoryViewModel

private const val USER_ID_PARAM = "USER_ID_PARAM"

class ExposeSaleHistoryFragment : Fragment() {
    companion object {
        val TAG:String = ExposeSaleHistoryFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(userId: Long) =
            ExposeSaleHistoryFragment().apply {
                arguments = Bundle().apply {
                    putLong(USER_ID_PARAM, userId)
                }
            }
    }

    private lateinit var binding : FragmentExposeSaleHistoryBinding
    private lateinit var invoiceAdapter: ExposeSaleHistoryAdapter
    private var userId : Long = 0L
    private val viewModel : ExposeSaleHistoryViewModel by viewModels()
    private var onClickInvoice : ((InvoiceModel)->Unit)? = null
    private var onClickBackPressed : (()->Unit)? = null

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
        binding =  FragmentExposeSaleHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView(){
        invoiceAdapter = ExposeSaleHistoryAdapter(
            onClickListener = { invoice ->
                onClickInvoice?.invoke(invoice)
            }
        )

        binding.rvSaleHistory.apply {
            adapter = invoiceAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
    }

    private fun setupViewModel(){
        viewModel.isLoading().observe(viewLifecycleOwner){ loading ->
            binding.lyProgressBar.root.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getInvoiceList().observe(viewLifecycleOwner){ invoiceList ->
            invoiceAdapter.submitList(invoiceList.toList())
        }
        viewModel.getTypeException().observe(viewLifecycleOwner){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                activity?.let { Functions.showSimpleMessage(it, exception.typeException.message) }
            }
        }

        viewModel.getAllInvoiceList(userId)
    }

    fun selectInvoice(onClickInvoice : ((InvoiceModel)->Unit)){
        this.onClickInvoice = onClickInvoice
    }

    fun onBackPressed(onClickBackPressed:()->Unit){
        this.onClickBackPressed=onClickBackPressed
    }
}