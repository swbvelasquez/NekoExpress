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
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.FragmentCheckoutCartBinding
import com.swbvelasquez.nekoexpress.domain.model.CartModel
import com.swbvelasquez.nekoexpress.ui.view.adapter.CheckoutCartAdapter
import com.swbvelasquez.nekoexpress.ui.viewmodel.CheckoutCartViewModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.CheckoutCartViewModelFactory

private const val CART_ID = "CART_ID"


class CheckoutCartFragment : Fragment() {
    companion object {
        val TAG:String = CheckoutCartFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(cartId: Long) =
            CheckoutCartFragment().apply {
                arguments = Bundle().apply {
                    putLong(CART_ID, cartId)
                }
            }
    }

    private lateinit var cart: CartModel
    private lateinit var binding: FragmentCheckoutCartBinding
    private lateinit var cartAdapter: CheckoutCartAdapter
    private var cartId: Long = 0
    private var onClickBackPressed: ((String)->Unit)? = null

    private val viewModel : CheckoutCartViewModel by viewModels {
        CheckoutCartViewModelFactory(cartId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle->
            cartId = bundle.getLong(CART_ID,0)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG,"onBackPressed")
                    onClickBackPressed?.invoke(ExposeCategoryFragment.TAG)
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
        binding = FragmentCheckoutCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
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
        viewModel.getCart().observe(viewLifecycleOwner){
            cart = it
            showTotalCart()
        }
    }

    private fun setupRecyclerView(){
        cartAdapter = CheckoutCartAdapter(
            onClickChangeQuantityListener = {
                val index = cart.productList.indexOf(it)
                cart.productList[index] = it
                cartAdapter.submitList(cart.productList.toList())
                calculateTotalCart()
            },
            onClickDeleteListener = {
                val index = cart.productList.indexOf(it)
                cart.productList.removeAt(index)
                cartAdapter.submitList(cart.productList.toList())
                calculateTotalCart()
            }
        )

        binding.rvProduct.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }
    }

    private fun calculateTotalCart(){
        cart.apply {
            subtotal = cart.productList.sumOf { it.total }
            taxes =  subtotal * Constants.TAXES_PERCENT
            total = subtotal + taxes
        }

        showTotalCart()
    }

    private fun showTotalCart(){
        activity?.let{
            binding.tvSubtotal.text = String.format(getString(R.string.format_price),cart.subtotal)
            binding.tvTaxes.text = String.format(getString(R.string.format_price),cart.taxes)
            binding.tvTotal.text = String.format(getString(R.string.format_price),cart.total)
        }
    }

    fun onBackPressed(onClickBackPressed:(String)->Unit){
        this.onClickBackPressed=onClickBackPressed
    }
}