package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.provider.GeoreferenceProvider
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.FragmentInvoiceDetailBinding
import com.swbvelasquez.nekoexpress.domain.model.InvoiceModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.ExposeSaleHistoryAdapter
import com.swbvelasquez.nekoexpress.ui.view.adapter.InvoiceDetailAdapter
import com.swbvelasquez.nekoexpress.ui.viewmodel.*

private const val INVOICE_ID_PARAM = "INVOICE_ID_PARAM"

class InvoiceDetailFragment : Fragment() {
    companion object {
        val TAG:String = InvoiceDetailFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(invoiceId: Long) =
            InvoiceDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(INVOICE_ID_PARAM, invoiceId)
                }
            }
    }

    private lateinit var binding : FragmentInvoiceDetailBinding
    private lateinit var invoiceDetailAdapter: InvoiceDetailAdapter
    private var invoiceId : Long = 0L
    private var onClickBackPressed: ((String)->Unit)? = null

    private val viewModel : InvoiceDetailViewModel by viewModels {
        InvoiceDetailViewModelFactory(invoiceId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            invoiceId = bundle.getLong(INVOICE_ID_PARAM,0)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG,"onBackPressed")
                    onClickBackPressed?.invoke(ExposeSaleHistoryFragment.TAG)
                    remove()
                }
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInvoiceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView(){
        invoiceDetailAdapter = InvoiceDetailAdapter()

        binding.rvDetail.apply {
            adapter = invoiceDetailAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
    }

    private fun setupViewModel(){
        viewModel.isLoading().observe(viewLifecycleOwner){ loading ->
            binding.lyProgressBar.root.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getInvoice().observe(viewLifecycleOwner){ invoice ->
            setupUi(invoice)
        }
        viewModel.getTypeException().observe(viewLifecycleOwner){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                activity?.let { Functions.showSimpleMessage(it, exception.typeException.message) }
            }
        }
    }

    private fun setupUi(invoice:InvoiceModel){
        with(binding){
            activity?.let {
                tvOrderNumber.text = String.format(it.getString(R.string.format_invoice_number),invoice.invoiceId)
                tvPurchaseDate.text = Functions.getFormattedDateFromLong(it.getString(R.string.format_date_default),invoice.date)
                tvSubtotal.text = String.format(getString(R.string.format_price_default),invoice.subtotal)
                tvTaxes.text = String.format(getString(R.string.format_price_default),invoice.taxes)
                tvTotal.text = String.format(getString(R.string.format_price_default),invoice.total)

                val department = GeoreferenceProvider.departmentMap[invoice.deliveryAddress.department] ?: ""
                val province = GeoreferenceProvider.provinceMap[invoice.deliveryAddress.province] ?: ""
                val district = GeoreferenceProvider.districtMap[invoice.deliveryAddress.district] ?: ""

                tvAddress.text = String.format(getString(R.string.format_invoice_delivery_address_summary),invoice.deliveryAddress.address,department,province,district)
            }
        }

        invoiceDetailAdapter.submitList(invoice.invoiceDetailList)
    }

    fun onBackPressed(onClickBackPressed:(String)->Unit){
        this.onClickBackPressed=onClickBackPressed
    }
}