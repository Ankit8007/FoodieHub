package com.app.foodiehub.presentation.model

data class ProductDataModel(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var price: Int? = null,
    var imageUrl: String = ""
)