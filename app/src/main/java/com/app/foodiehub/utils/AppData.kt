package com.app.foodiehub.utils

import com.app.foodiehub.presentation.model.BannerDataModel
import com.app.foodiehub.presentation.model.CartDataModel
import com.app.foodiehub.presentation.model.ProductDataModel

class AppData {
    companion object {
        var prodList: List<ProductDataModel> = arrayListOf()
        var bannerList: List<BannerDataModel> = arrayListOf()
        var cartList: List<CartDataModel> = arrayListOf()
    }
}