package com.app.foodiehub.presentation.view.homeFragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodiehub.core.BaseResponse
import com.app.foodiehub.databinding.FragmentCartTabBinding
import com.app.foodiehub.presentation.view.adapter.CartItemAdapter
import com.app.foodiehub.presentation.viewModel.FoodViewModel
import com.app.foodiehub.presentation.viewModel.VMScope
import com.app.foodiehub.utils.AppData
import com.app.foodiehub.utils.AppUtils
import com.app.foodiehub.utils.toast

class CartTab : Fragment() {
    private lateinit var binding: FragmentCartTabBinding
    private val foodViewModel: FoodViewModel = VMScope.foodViewModel
    private lateinit var cartDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartTabBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartDialog = AppUtils.cartDialog(view.context){
            cartDialog.dismiss()
        }
        binding.cartList.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        setList()
        foodViewModel.observeCartListLiveData.observe(viewLifecycleOwner) { observe(it) }
        foodViewModel.observeOrderLiveData.observe(viewLifecycleOwner) { observeOrder(it) }

        binding.proccedBtn.setOnClickListener {
            foodViewModel.onOrderAction()
        }
    }

    private fun observeOrder(result: BaseResponse<String?>) {
        when (result) {
            is BaseResponse.Success -> cartDialog.show()
            else -> {}
        }
    }

    private fun observe(result: BaseResponse<String?>) {
        when (result) {
            is BaseResponse.Success -> setList()

            is BaseResponse.Error -> result.msg?.let { requireContext().toast(it) }

            else -> {}
        }
    }

    private fun setList() {
         binding.proccedBtn.visibility = if(AppData.cartList.size > 0) View.VISIBLE else View.GONE
        binding.cartTotal.text = "${ foodViewModel.cartTotal }"
        binding.cartList.adapter = CartItemAdapter(AppData.cartList) { data, type ->
            foodViewModel.updateCart(
                data.id,
                type
            )
        }
    }

}