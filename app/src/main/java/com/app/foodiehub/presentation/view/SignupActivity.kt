package com.app.foodiehub.presentation.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.foodiehub.core.BaseResponse
import com.app.foodiehub.databinding.ActivitySignupBinding
import com.app.foodiehub.presentation.model.UserDataModel
import com.app.foodiehub.presentation.model.UserDetailDataModel
import com.app.foodiehub.presentation.viewModel.AuthViewModel
import com.app.foodiehub.presentation.viewModel.VMScope
import com.app.foodiehub.utils.AppUtils
import com.app.foodiehub.utils.FormValidationEnum
import com.app.foodiehub.utils.getValue
import com.app.foodiehub.utils.toast

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel: AuthViewModel = VMScope.authViewModel
    private lateinit var loader: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loader = AppUtils.showLoader(this)

        binding.backBtn.backBtn.setOnClickListener {
            finish()
        }

        binding.loginText.setOnClickListener {
            finish()
        }

        viewModel.observeSignUpLiveDataResult().observe(
            this
        ) { observeData(it) }

        binding.createBtn.setOnClickListener {
            viewModel.onCreateAccountAction(
                binding.email.getValue,
                binding.password.getValue,
                binding.name.getValue,
                binding.phone.getValue
            )
        }

    }

    private fun observeData(it: BaseResponse<UserDataModel<UserDetailDataModel>>) {
        when (it) {
            is BaseResponse.Loading -> startLoading()

            is BaseResponse.Success -> {
                stopLoading()
                this.toast("Registered Successfully")
            }

            is BaseResponse.FormValidationError -> {
                stopLoading()
                when (it.key) {
                    FormValidationEnum.email -> binding.email.setError(it.msg)
                    FormValidationEnum.password -> binding.password.setError(it.msg)
                    FormValidationEnum.username -> binding.name.setError(it.msg)
                    FormValidationEnum.phone -> binding.phone.setError(it.msg)
                    else -> it.msg?.let { this.toast(it) }
                }
            }

            is BaseResponse.Error -> {
                stopLoading()
                it.msg?.let { this.toast(it) }
            }

            else -> stopLoading()

        }
    }

    private fun startLoading() = loader.show()

    private fun stopLoading() = loader.dismiss()

}