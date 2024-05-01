package com.app.foodiehub.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.foodiehub.core.BaseResponse
import com.app.foodiehub.databinding.ActivityProductDetailBinding
import com.app.foodiehub.presentation.viewModel.FoodViewModel
import com.app.foodiehub.presentation.viewModel.VMScope
import com.app.foodiehub.utils.loadUrl
import com.app.foodiehub.utils.toast

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val foodViewModel: FoodViewModel = VMScope.foodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backBtn.backBtn.setOnClickListener {
            finish()
        }

        binding.addCartBtn.setOnClickListener {
            foodViewModel.observeSingleProdtLiveData.value?.let {
                foodViewModel.addToCart(
                    it
                )
            }
        }

        foodViewModel.observeSingleProdtLiveData.observe(this) {
            if (it != null) {
                binding.name.setText(it.name)
                binding.image.loadUrl(it.imageUrl)
                binding.price.setText("${it.price}")
                binding.description.setText(it.description)
            }
        }

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
}