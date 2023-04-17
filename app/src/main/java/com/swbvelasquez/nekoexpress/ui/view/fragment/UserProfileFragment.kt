package com.swbvelasquez.nekoexpress.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.swbvelasquez.nekoexpress.R
import com.swbvelasquez.nekoexpress.core.error.CustomTypeException
import com.swbvelasquez.nekoexpress.core.util.Functions
import com.swbvelasquez.nekoexpress.databinding.FragmentPaymentDetailBinding
import com.swbvelasquez.nekoexpress.databinding.FragmentUserProfileBinding
import com.swbvelasquez.nekoexpress.domain.model.UserModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.PaymentDetailViewModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.PaymentDetailViewModelFactory
import com.swbvelasquez.nekoexpress.ui.viewmodel.UserProfileViewModel
import com.swbvelasquez.nekoexpress.ui.viewmodel.UserProfileViewModelFactory

private const val EMAIL_PARAM = "EMAIL_PARAM"

class UserProfileFragment : Fragment() {
    companion object {
        val TAG:String = UserProfileFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(email: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_PARAM, email)
                }
            }
    }

    private lateinit var binding: FragmentUserProfileBinding
    private var email: String = ""
    private var onClickBackPressed: (()->Unit)? = null

    private val viewModel : UserProfileViewModel by viewModels {
        UserProfileViewModelFactory(email)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            email = bundle.getString(EMAIL_PARAM,"")
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
        binding =  FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        viewModel.getUser().observe(viewLifecycleOwner){ user ->
            setupUI(user)
        }
    }

    private fun setupUI(user:UserModel){
        with(binding){
            val completeName = "${user.firstName} ${user.lastName}"
            tvCompleteName.text = completeName
            tvEmail.text = user.email
            tvPhone.text = user.phone
            tvRegisterDate.text = Functions.getFormattedDateFromLong(getString(R.string.format_date_default),user.registerDate)

            Glide
                .with(this@UserProfileFragment)
                .load(user.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvThumbnail)
        }
    }

    fun onBackPressed(onClickBackPressed:()->Unit){
        this.onClickBackPressed=onClickBackPressed
    }
}