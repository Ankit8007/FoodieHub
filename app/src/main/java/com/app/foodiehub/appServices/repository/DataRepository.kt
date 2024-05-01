package com.app.foodiehub.appServices.repository

import com.app.foodiehub.appServices.firebase.AuthService
import com.app.foodiehub.appServices.firebase.DBServices
import com.app.foodiehub.appServices.local.LocalDBServices
import com.app.foodiehub.presentation.model.BannerDataModel
import com.app.foodiehub.presentation.model.CartModel
import com.app.foodiehub.presentation.model.LoginUser
import com.app.foodiehub.presentation.model.ProductDataModel
import com.app.foodiehub.presentation.model.RegisterUser
import com.app.foodiehub.presentation.model.UpdateUserDetailModel
import com.app.foodiehub.presentation.model.UserDataModel
import com.app.foodiehub.presentation.model.UserDetailDataModel
import com.google.firebase.auth.FirebaseUser

class DataRepository: IDataRepository {
    override suspend fun createAccount(data: RegisterUser): FirebaseUser {
        try {
            val authUser =  AuthService.createAccount(data.userData.email,data.password)
            if (authUser != null) {
                DBServices.createUser(authUser.uid,data.userData)
                return authUser
            } else {
                throw  Exception("User creation failed.")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun login(data: LoginUser): Boolean {
        try {
            val authUser =  AuthService.auth(data.email,data.password)
            if (authUser != null) {
                return true
            } else {
                throw Exception("login failed")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateUser(uid: String, data: UpdateUserDetailModel): Boolean {
        try {
            return  DBServices.updateUser(uid,data)
        } catch (e: Exception) {
            throw e
        }
    }

    override  fun isAuth(): Boolean = AuthService.isAuth()

    override suspend fun getUserData(): UserDataModel<UserDetailDataModel> {
        try {
            val uid = AuthService.getUid()!!
            val data = DBServices.getUser(uid)
            return  UserDataModel(uid,data!!)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getProductList(): List<ProductDataModel> {
        try {
           return DBServices.getProductList()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getBannerList(): List<BannerDataModel> {
        try {
            return DBServices.getBanners()
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun addCart(list: List<CartModel>) {
        try {
            LocalDBServices.addCart(list)
        }catch (e:Exception) {
            throw e
        }
    }

    override suspend fun getCart(): List<CartModel> {
        try {
            return LocalDBServices.getCart()
        }catch (e:Exception) {
            throw e
        }
    }

}