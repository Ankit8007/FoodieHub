package com.app.foodiehub.presentation.view.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodiehub.core.BaseResponse
import com.app.foodiehub.databinding.FragmentHomeTabBinding
import com.app.foodiehub.presentation.model.BannerDataModel
import com.app.foodiehub.presentation.model.ProductDataModel
import com.app.foodiehub.presentation.view.ProductDetailActivity
import com.app.foodiehub.presentation.view.adapter.BannerItemAdapterHome
import com.app.foodiehub.presentation.view.adapter.PopularItemAdapterHome
import com.app.foodiehub.presentation.viewModel.FoodViewModel
import com.app.foodiehub.utils.AppData
import com.app.foodiehub.utils.Navigator


class HomeTab : Fragment() {
    private lateinit var binding: FragmentHomeTabBinding
    private val foodViewModel: FoodViewModel by activityViewModels()
    private lateinit var prodAdapter: PopularItemAdapterHome

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeTabBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bannerList.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
        )
        binding.popularList.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
        )

        binding.refresh.setOnRefreshListener {
            foodViewModel.getProductList(true)
        }

        setProductList()
        setBannerList()

        foodViewModel.observeProductListLiveData.observe(viewLifecycleOwner) { observeProdList(it) }
        foodViewModel.observeBannerListLiveData.observe(viewLifecycleOwner) { observeBannerList(it) }
    }


    private fun setProductList() {
        prodAdapter = PopularItemAdapterHome(AppData.prodList,
            itemClickListener = {
                foodViewModel.getSingleProduct(it)
                Navigator.push(requireContext(), ProductDetailActivity::class.java)
            },
            onAdd = { foodViewModel.addToCart(it) }
        )
        binding.popularList.adapter = prodAdapter

    }

    private fun setBannerList() {
        binding.bannerList.adapter = BannerItemAdapterHome(AppData.bannerList)

    }

    private fun observeBannerList(list: List<BannerDataModel>?) {
        if (list != null) setBannerList()
    }

    private fun observeProdList(result: BaseResponse<List<ProductDataModel>>) {
        when (result) {

            is BaseResponse.Success -> {
                if (binding.refresh.isRefreshing) {
                    binding.refresh.isRefreshing = false
                }
                if (result.data != null) {
                    setProductList()
                }
            }

            else -> {}
        }
    }

}