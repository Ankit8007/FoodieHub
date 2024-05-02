package com.app.foodiehub.presentation.model

import com.app.foodiehub.utils.isNameValid
import com.app.foodiehub.utils.isPhoneValid
import com.app.foodiehub.utils.serializeToMap
import com.google.gson.annotations.SerializedName

class UpdateUserDetailModel (
    @SerializedName("name")
    var name: String = "",

    @SerializedName("phone")
    var phone: String = ""
): ILoginUser{
    fun toMap(): Map<String,Any> = this.serializeToMap()
    override fun isEmailValid(): Boolean {
        return  true
    }

    override fun isPassword(): Boolean {
        return  true
    }

    override fun validName(): Boolean {
        return  name.isNameValid()
    }

    override fun validPhoneNo(): Boolean {
        return  phone.isPhoneValid()
    }
}