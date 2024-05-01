package com.app.foodiehub.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.foodiehub.appServices.repository.DataRepository
import com.app.foodiehub.appServices.repository.IDataRepository
import com.app.foodiehub.core.BaseResponse
import com.app.foodiehub.presentation.model.BannerDataModel
import com.app.foodiehub.presentation.model.CartDataModel
import com.app.foodiehub.presentation.model.CartModel
import com.app.foodiehub.presentation.model.ProductDataModel
import com.app.foodiehub.utils.AppData
import com.app.foodiehub.utils.CartEnum
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {
    private val productMutableLiveData: MutableLiveData<BaseResponse<List<ProductDataModel>>> =
        MutableLiveData()
    private val searchListMutableLiveData: MutableLiveData<List<ProductDataModel>> =
        MutableLiveData()
    private val bannerMutableLiveData: MutableLiveData<List<BannerDataModel>?> = MutableLiveData()
    private val singleProdMutableLiveData: MutableLiveData<ProductDataModel> = MutableLiveData()
    private val cartListMutableLiveData: MutableLiveData<BaseResponse<String?>> =
        MutableLiveData()
    private val orderMutableLiveData: MutableLiveData<BaseResponse<String?>> =
        MutableLiveData()

    private var productList: List<ProductDataModel> = arrayListOf()
    private var cartList: MutableList<CartDataModel> = mutableListOf()

    private val repository: IDataRepository

    var cartTotal: Double = 0.0

    init {
        this.repository = DataRepository()
    }

    fun getProductList(isRefresh: Boolean = false) {
        if (!isRefresh) productMutableLiveData.value = BaseResponse.Loading()

        viewModelScope.launch {
            try {
                productList = repository.getProductList()
                AppData.prodList = productList

                AppData.bannerList = repository.getBannerList()
                bannerMutableLiveData.value = AppData.bannerList
                productMutableLiveData.value = BaseResponse.Success(productList)

                if (!isRefresh) {
                    getCartList()
                }
            } catch (e: Exception) {
                productMutableLiveData.value = BaseResponse.Error(e.message.toString())
            }
        }
    }

    fun getSingleProduct(data: ProductDataModel) {
        singleProdMutableLiveData.value = data
    }

    fun getSearchProdListInit() {
        searchListMutableLiveData.value = productList
    }

    fun onSearchProd(search: String) {
        if (search.isEmpty()) {
            getSearchProdListInit()
        } else {
            val filteredList =
                productList.filter { product -> product.name.contains(search, ignoreCase = true) }
            searchListMutableLiveData.value = filteredList
        }
    }

    fun getCartList() {
        viewModelScope.launch {
            try {
                val list: List<CartModel> = repository.getCart()
                val _list = list
                    .mapNotNull { data ->
                        productList.find { it.id == data._id }?.let {
                            CartDataModel(it.id, data._qty, it)
                        }
                    }
                cartOnSuccess(_list.toMutableList())
            } catch (e: Exception) {
                cartListMutableLiveData.value = BaseResponse.Error(e.toString())
                cartListMutableLiveData.value = BaseResponse.Error(null)
            }
        }
    }

    fun onOrderAction(){
        viewModelScope.launch {
            try {
                repository.addCart(arrayListOf())
                cartOnSuccess(arrayListOf(), null)
                orderMutableLiveData.value = BaseResponse.Success()
                orderMutableLiveData.value = BaseResponse.Error(null)
            } catch (e: Exception) {
                cartListMutableLiveData.value = BaseResponse.Error(e.message)
                cartListMutableLiveData.value = BaseResponse.Error(null)
            }
        }
    }

    private fun cartSaveToDB(list: MutableList<CartDataModel>, msg: String? = null) {
        val _list = list.map { data -> data.cartModel }
        viewModelScope.launch {
            try {
                repository.addCart(_list)
                cartOnSuccess(list, msg)
            } catch (e: Exception) {
                cartListMutableLiveData.value = BaseResponse.Error(e.message)
                cartListMutableLiveData.value = BaseResponse.Error(null)
            }
        }
    }

    private fun cartOnSuccess(list: MutableList<CartDataModel>, msg: String? = null) {
        cartList = list
        AppData.cartList = cartList
        calculateCartTotal()
        cartListMutableLiveData.value = BaseResponse.Success(msg)
        cartListMutableLiveData.value = BaseResponse.Success(null)
    }

    fun addToCart(data: ProductDataModel) {
        if (cartList.any { it.id == data.id }) {
            updateCart(data.id, CartEnum.ADD, "Updated cart successfully")
        } else {
            val list = cartList
            val item = CartDataModel(data.id, 1, data)
            list.add(0, item)
            cartSaveToDB(list, "Add to cart successfully")
        }
    }

    fun updateCart(prodId: String, type: CartEnum, msg: String? = null) {
        val list = cartList
        val index = list.indexOfFirst { it.id == prodId }
        if (index > -1) {
            val value = list[index]
            when (type) {
                CartEnum.ADD -> {
                    value.qty++
                    list.set(index, value)
                    cartSaveToDB(list, msg)
                }

                CartEnum.SUBTRACT -> {
                    value.qty--
                    if (value.qty > 0) {
                        list.set(index, value)
                    } else {
                        list.removeAt(index)
                    }
                    cartSaveToDB(list)
                }

                CartEnum.REMOVE -> {
                    list.removeAt(index)
                    cartSaveToDB(list)
                }

                else -> {

                }
            }
        }
    }

    val observeProductListLiveData: LiveData<BaseResponse<List<ProductDataModel>>> get() = productMutableLiveData
    val observeBannerListLiveData: LiveData<List<BannerDataModel>?> get() = bannerMutableLiveData

    val observeSearchListLiveData: LiveData<List<ProductDataModel>> get() = searchListMutableLiveData

    val observeSingleProdtLiveData: LiveData<ProductDataModel> get() = singleProdMutableLiveData

    val observeCartListLiveData: LiveData<BaseResponse<String?>> get() = cartListMutableLiveData
    val observeOrderLiveData: LiveData<BaseResponse<String?>> get() = orderMutableLiveData


    private fun calculateCartTotal() {
        cartTotal = AppData.cartList.fold(0.0, operation = {count,item ->
            count + (item.qty * (item.data.price ?: 0) ) })
    }
}