package com.app.foodiehub.presentation.model

interface ILoginUser {
    fun isEmailValid(): Boolean
    fun isPassword(): Boolean
    fun validName(): Boolean
    fun validPhoneNo(): Boolean
}