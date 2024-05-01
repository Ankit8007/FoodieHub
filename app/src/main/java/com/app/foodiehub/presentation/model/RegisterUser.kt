package com.app.foodiehub.presentation.model

import com.app.foodiehub.utils.isEmailValid
import com.app.foodiehub.utils.isNameValid
import com.app.foodiehub.utils.isPasswordValid
import com.app.foodiehub.utils.isPhoneValid

class RegisterUser(
    val userData: UserDetailDataModel,
    val password: String,
): ILoginUser{
    override fun isEmailValid(): Boolean {
        return userData.email.isEmailValid()
    }

    override fun isPassword(): Boolean {
        return password.isPasswordValid()
    }

    override fun validName(): Boolean {
        return userData.name.isNameValid()
    }

    override fun validPhoneNo(): Boolean {
        return userData.phone.isPhoneValid()
    }
}