package com.app.foodiehub.presentation.view

import android.app.Dialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.foodiehub.R
import com.app.foodiehub.appServices.local.LocalDBServices
import com.app.foodiehub.core.BaseResponse
import com.app.foodiehub.databinding.ActivityHomeBinding
import com.app.foodiehub.presentation.viewModel.AuthViewModel
import com.app.foodiehub.presentation.viewModel.FoodViewModel
import com.app.foodiehub.presentation.viewModel.VMScope
import com.app.foodiehub.utils.AppUtils
import com.app.foodiehub.utils.toast

class HomeActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val foodViewModel: FoodViewModel by viewModels()

    private lateinit var loader: Dialog
    private var networkCallMade = false

    override fun onStart() {
        super.onStart()

        if (!networkCallMade) {
            LocalDBServices.setContext(this)
            authViewModel.getUserData()
            foodViewModel.getProductList(false)
            foodViewModel.getCartList()
            networkCallMade = true
        }

    }

    private lateinit var bindig: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bindig.root)
        loader = AppUtils.showLoader(this)
        VMScope.injectAuth(authViewModel)
        VMScope.injectFood(foodViewModel)

        var navController: NavController = findNavController(R.id.tabFragment)

        bindig.bottomNavigationView.setupWithNavController(navController)

        foodViewModel.observeProductListLiveData.observe(this) { observer(it) }
        authViewModel.observeProfileDataLiveDataResult.observe(this) { observer(it) }
        foodViewModel.observeCartListLiveData.observe(this) { observeCart(it) }
    }

    private fun observeCart(result: BaseResponse<String?>) {
        when (result) {
            is BaseResponse.Success -> {
                result.data?.let { this.toast(it) }
            }

            is BaseResponse.Error -> result.msg?.let { this.toast(it) }
            else -> {}
        }
    }

    private fun observer(result: BaseResponse<Any>) {
        when (result) {
            is BaseResponse.Loading -> startLoading()
            is BaseResponse.Success -> stopLoading()

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