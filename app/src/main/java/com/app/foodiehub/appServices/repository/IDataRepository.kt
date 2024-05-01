package com.app.foodiehub.appServices.repository

import com.app.foodiehub.presentation.model.BannerDataModel
import com.app.foodiehub.presentation.model.CartModel
import com.app.foodiehub.presentation.model.LoginUser
import com.app.foodiehub.presentation.model.ProductDataModel
import com.app.foodiehub.presentation.model.RegisterUser
import com.app.foodiehub.presentation.model.UpdateUserDetailModel
import com.app.foodiehub.presentation.model.UserDataModel
import com.app.foodiehub.presentation.model.UserDetailDataModel
import com.google.firebase.auth.FirebaseUser

interface IDataRepository {
    suspend fun createAccount(data: RegisterUser): FirebaseUser?
    suspend fun login(data: LoginUser): Boolean
    suspend fun updateUser(uid: String,data: UpdateUserDetailModel): Boolean
    fun isAuth(): Boolean
    suspend fun getUserData(): UserDataModel<UserDetailDataModel>
    suspend fun getProductList(): List<ProductDataModel>
    suspend fun getBannerList(): List<BannerDataModel>
    suspend fun addCart(list: List<CartModel>)
    suspend fun getCart(): List<CartModel>
}