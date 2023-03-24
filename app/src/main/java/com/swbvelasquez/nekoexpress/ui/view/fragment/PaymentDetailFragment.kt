package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.FragmentPaymentDetailBinding
import com.swbvelasquez.nekoexpress.domain.model.DeliveryAddressModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.PaymentDetailViewModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.PaymentDetailViewModelFactory

private const val CART_ID = "CART_ID"
private const val CART_TOTAL = "CART_TOTAL"

class PaymentDetailFragment : Fragment() {
    companion object {
        val TAG:String = CheckoutCartFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(cartId: Long,total: Double) =
            PaymentDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(CART_ID, cartId)
                    putDouble(CART_TOTAL, total)
                }
            }
    }

    private lateinit var binding: FragmentPaymentDetailBinding
    private var cartId: Long = 0
    private var cartTotal: Double = 0.0
    private var onClickBackPressed: ((String)->Unit)? = null
    private var onClickConfirmOrder: (()->Unit)? = null

    private val viewModel : PaymentDetailViewModel by viewModels {
        PaymentDetailViewModelFactory(cartId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            cartId = bundle.getLong(CART_ID,0)
            cartTotal = bundle.getDouble(CART_TOTAL,0.0)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG,"onBackPressed")
                    onClickBackPressed?.invoke(CheckoutCartFragment.TAG)
                    remove()
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  FragmentPaymentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupButtons()
        setupViewModel()
    }

    private fun setupViewModel(){
        viewModel.isLoading().observe(viewLifecycleOwner){ loading ->
            binding.lyProgressBar.root.visibility =  if(loading) View.VISIBLE else View.GONE
        }
        viewModel.getTypeException().observe(viewLifecycleOwner){ exception ->
            if(exception.typeException != CustomTypeException.NONE) {
                activity?.let { Functions.showSimpleMessage(it, exception.typeException.message) }
            }
        }
        viewModel.getResult().observe(viewLifecycleOwner){ result ->
            if(result is Boolean) {
                activity?.let {
                    val dialog = if (result) {
                        MaterialAlertDialogBuilder(it)
                            .setTitle(resources.getString(R.string.dialog_confirm_order_success_title))
                            .setMessage(resources.getString(R.string.dialog_confirm_order_success_message))
                            .setPositiveButton(resources.getString(R.string.dialog_confirm_order_success_positive_button)) { _, _ ->
                                onClickConfirmOrder?.invoke()
                            }
                            .setCancelable(false)
                    } else {
                        MaterialAlertDialogBuilder(it)
                            .setTitle(resources.getString(R.string.dialog_confirm_order_fail_title))
                            .setMessage(resources.getString(R.string.dialog_confirm_order_fail_message))
                            .setPositiveButton(resources.getString(R.string.dialog_confirm_order_fail_positive_button)) { _, _ -> }
                            .setCancelable(false)
                    }

                    dialog.show()
                }
            }
        }
    }

    private fun setupButtons(){
        binding.btnConfirmOrder.setOnClickListener {
            confirmOrder()
        }
    }

    private fun setupUI(){
        binding.tvTotalOrder.text = String.format(getString(R.string.format_price),cartTotal)
    }

    private fun confirmOrder(){
        val deliveryAddress = DeliveryAddressModel("","","","","")

        viewModel.confirmOrder(deliveryAddress)
    }

    fun onBackPressed(onClickBackPressed:(String)->Unit){
        this.onClickBackPressed=onClickBackPressed
    }

    fun onConfirmOrder(onClickConfirmOrder:()->Unit){
        this.onClickConfirmOrder = onClickConfirmOrder
    }
}