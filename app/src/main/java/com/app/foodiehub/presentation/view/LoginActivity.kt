package com.app.foodiehub.presentation.view

import android.app.Dialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.app.foodiehub.core.BaseResponse
import com.app.foodiehub.databinding.ActivityLoginBinding
import com.app.foodiehub.presentation.model.UserDataModel
import com.app.foodiehub.presentation.model.UserDetailDataModel
import com.app.foodiehub.presentation.viewModel.AuthViewModel
import com.app.foodiehub.presentation.viewModel.VMScope
import com.app.foodiehub.utils.AppUtils
import com.app.foodiehub.utils.Navigator
import com.app.foodiehub.utils.getValue
import com.app.foodiehub.utils.toast

class LoginActivity : ComponentActivity() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loader: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        VMScope.injectAuth(viewModel)

        loader = AppUtils.showLoader(this)

        binding.loginButton.setOnClickListener {
            viewModel.onLoginAction(
                binding.email.getValue,
                binding.password.getValue
            )
        }

        binding.signUp.setOnClickListener {
            Navigator.push(this, SignupActivity::class.java)
        }



        viewModel.observeLoginLiveDataResult().observe(this) { result -> observeData(result) }

    }

    private fun observeData(result: BaseResponse<UserDataModel<UserDetailDataModel>>) {
        when (result) {
            is BaseResponse.Loading -> startLoading()

            is BaseResponse.Success -> {
                stopLoading()
                Navigator.pushAndReplace(this, HomeActivity::class.java)
                this.toast("Login Success")
            }

            is BaseResponse.Error -> {
                stopLoading()
                result.msg?.let { this.toast(it) }
            }

            else -> stopLoading()
        }
    }

    private fun startLoading() = loader.show()

    private fun stopLoading() = loader.dismiss()

}