package com.app.foodiehub.presentation.model

import com.google.gson.annotations.SerializedName

data class UserDataModel<T>(
    @SerializedName("uid")
    var uid: String,
    @SerializedName("data")
    var data: T,
){
    fun copyData(data: T): UserDataModel<T>{
        return  UserDataModel(
            this.uid,
            data
        )
    }
}