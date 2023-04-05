package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.provider.GeoreferenceProvider
import com.swbvelasquez.nekoexpress.core.util.Constants
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.FragmentPaymentDetailBinding
import com.swbvelasquez.nekoexpress.domain.model.DeliveryAddressModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.PaymentDetailViewModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.PaymentDetailViewModelFactory

private const val CART_ID = "CART_ID"
private const val CART_TOTAL = "CART_TOTAL"

class PaymentDetailFragment : Fragment() {
    companion object {
        val TAG:String = PaymentDetailFragment::class.java.simpleName

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
    private var keyDepartment:String = ""
    private var keyProvince:String = ""
    private var keyDistrict:String = ""
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
        setupDepartmentAutoCompleteTextView()
    }

    private fun setupDepartmentAutoCompleteTextView(){
        val departmentList = GeoreferenceProvider.departmentMap.keys.toList()
        val departmentAdapter = activity?.let { ArrayAdapter(it,R.layout.item_dropdown_general,GeoreferenceProvider.departmentMap.values.toList()) }

        binding.acvDepartment.apply {
            setAdapter(departmentAdapter)
            keyListener = null
            onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    if(keyDepartment != departmentList[position]) {
                        keyDepartment = departmentList[position]
                        setupProvinceAutoCompleteTextView()
                    }
                }
        }
    }

    private fun setupProvinceAutoCompleteTextView(){
        val provinceMap = GeoreferenceProvider.provinceMap.filter { it.key.startsWith(keyDepartment) }
        val provinceList = provinceMap.keys.toList()
        val provinceAdapter = activity?.let { ArrayAdapter(it,R.layout.item_dropdown_general, provinceMap.values.toList()) }

        binding.acvProvince.apply {
            setAdapter(provinceAdapter)
            keyListener = null
            onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    if(keyProvince != provinceList[position]) {
                        keyProvince = provinceList[position]
                        setupDistrictAutoCompleteTextView()
                    }
                }
        }
    }

    private fun setupDistrictAutoCompleteTextView(){
        val districtMap = GeoreferenceProvider.districtMap.filter { it.key.startsWith(keyProvince) }
        val districtList = districtMap.keys.toList()
        val districtAdapter = activity?.let { ArrayAdapter(it,R.layout.item_dropdown_general, districtMap.values.toList()) }

        binding.acvDistrict.apply {
            setAdapter(districtAdapter)
            keyListener = null
            onItemClickListener =
                OnItemClickListener { _, _, position, _ ->
                    if(keyDistrict != districtList[position]) {
                        keyDistrict = districtList[position]
                    }
                }
        }
    }

    private fun confirmOrder(){
        if(validateFields()) {
            val deliveryAddress = DeliveryAddressModel(
                keyDepartment, keyProvince, keyDistrict,
                binding.etAddress.text.toString().trim(),
                binding.etPhone.text.toString().trim()
            )

            viewModel.confirmOrder(deliveryAddress)
        }
    }

    private fun validateEmptyFields(vararg textFields: TextInputLayout):Boolean{
        var isValid = true

        for (textField in textFields){
            if (textField.editText?.text.toString().trim().isEmpty()){
                textField.error = resources.getString(R.string.text_empty_field_validation)
                isValid = false
            } else {
                textField.error = null
            }
        }

        return isValid
    }

    private fun validateRegexMatchFields(vararg textFields: TextInputLayout,regex:String):Boolean{
        var isValid = true

        for (textField in textFields){
            if (Functions.isValidRegexFormat(textField.editText?.text.toString().trim(),regex)){
                textField.error = resources.getString(R.string.text_match_regex_validation)
                textField.helperText = regex
                isValid = false
            } else {
                textField.error = null
                textField.helperText = null
            }
        }

        return isValid
    }

    private fun validateFields():Boolean{
        val isValid =
            with(binding) {
                validateEmptyFields(tilCardNumber,tilExpireDate,tilCVV,tilCardHolder,tilDepartment,tilProvince,tilDistrict,tilAddress,tilPhone)
                        && validateRegexMatchFields(tilExpireDate, regex = Constants.REGEX_CARD_EXPIRE_DATE)
                        && validateRegexMatchFields(tilPhone, regex = Constants.REGEX_PHONE)
            }

        if(!isValid) {
            activity?.let { Functions.showSimpleMessage(it,resources.getString(R.string.text_general_message_error)) }
        }

        return isValid
    }

    fun onBackPressed(onClickBackPressed:(String)->Unit){
        this.onClickBackPressed=onClickBackPressed
    }

    fun onConfirmOrder(onClickConfirmOrder:()->Unit){
        this.onClickConfirmOrder = onClickConfirmOrder
    }
}