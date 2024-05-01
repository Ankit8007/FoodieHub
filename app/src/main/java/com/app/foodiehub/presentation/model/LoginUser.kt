package com.app.foodiehub.presentation.model

import com.app.foodiehub.utils.isEmailValid
import com.app.foodiehub.utils.isPasswordValid

class LoginUser(val email:String,val password: String): ILoginUser{
    override fun isEmailValid(): Boolean {
        return email.isEmailValid()
    }

    override fun isPassword(): Boolean {
        return password.isPasswordValid()
    }

    override fun validName(): Boolean {
        return false
    }

    override fun validPhoneNo(): Boolean {
        return false
    }
}
