package com.app.foodiehub.presentation.model

data class CartDataModel(
    var id: String,
    var qty: Int,
    var data: ProductDataModel
){
    val cartModel: CartModel get() = CartModel(id,qty)
}
