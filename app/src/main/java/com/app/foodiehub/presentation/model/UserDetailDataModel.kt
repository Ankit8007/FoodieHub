package com.app.foodiehub.presentation.model

import com.google.gson.annotations.SerializedName

data class UserDetailDataModel(
    @SerializedName("email")
    var email: String = "",

    @SerializedName("name")
    var name: String = "",

    @SerializedName("phone")
    var phone: String = ""
){
    fun copy(name: String,phone: String): UserDetailDataModel{
        return UserDetailDataModel(
            this.email,
            name,
            phone
        )
    }
}
