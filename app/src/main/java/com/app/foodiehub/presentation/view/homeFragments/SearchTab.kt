package com.app.foodiehub.presentation.view.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.foodiehub.databinding.FragmentSearchTabBinding
import com.app.foodiehub.presentation.model.ProductDataModel
import com.app.foodiehub.presentation.view.ProductDetailActivity
import com.app.foodiehub.presentation.view.adapter.PopularItemAdapterHome
import com.app.foodiehub.presentation.viewModel.FoodViewModel
import com.app.foodiehub.utils.Navigator
import com.app.foodiehub.utils.hideKeyboard


class SearchTab : Fragment() {
    private lateinit var binding: FragmentSearchTabBinding
    private val foodViewModel: FoodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchTabBinding.inflate(
            LayoutInflater.from(inflater.context),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelInit()
        clearSearchText()
        searchTextListener()

        binding.searchList.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        setSearchList(arrayListOf())
    }

    private fun viewModelInit() {
        foodViewModel.observeSearchListLiveData.observe(viewLifecycleOwner) { observeSearchList(it) }
        foodViewModel.getSearchProdListInit()
    }

    private fun clearSearchText() {
        binding.searchView.setQuery("", false)
        binding.searchView.clearFocus()
    }

    private fun searchTextListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(serach: String): Boolean {

                foodViewModel.onSearchProd(serach.trim())
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                view!!.let { it.context.hideKeyboard(it) }
                return true
            }
        })
    }

    private fun observeSearchList(productDataModels: List<ProductDataModel>) {
        setSearchList(productDataModels)
    }

    private fun setSearchList(list: List<ProductDataModel>) {
        binding.searchList.adapter = PopularItemAdapterHome(list,
            itemClickListener = {
                foodViewModel.getSingleProduct(it)
                Navigator.push(requireContext(), ProductDetailActivity::class.java)
            },
            onAdd = { foodViewModel.addToCart(it) })
    }
}