package com.app.foodiehub.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.foodiehub.appServices.repository.DataRepository
import com.app.foodiehub.appServices.repository.IDataRepository
import com.app.foodiehub.core.BaseResponse
import com.app.foodiehub.presentation.model.LoginUser
import com.app.foodiehub.presentation.model.RegisterUser
import com.app.foodiehub.presentation.model.UpdateUserDetailModel
import com.app.foodiehub.presentation.model.UserDataModel
import com.app.foodiehub.presentation.model.UserDetailDataModel
import com.app.foodiehub.utils.AppUtils
import com.app.foodiehub.utils.FormValidationEnum
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository: IDataRepository

    private val userMutableLiveData: MutableLiveData<UserDataModel<UserDetailDataModel>?> =
        MutableLiveData()
    private val loginResult: MutableLiveData<BaseResponse<UserDataModel<UserDetailDataModel>>> =
        MutableLiveData()
    private val registerResult: MutableLiveData<BaseResponse<UserDataModel<UserDetailDataModel>>> =
        MutableLiveData()
    private val updateProfileResult: MutableLiveData<BaseResponse<Boolean>> =
        MutableLiveData()

    init {
        repository = DataRepository()
    }

    fun onCreateAccountAction(
        email: String,
        password: String,
        name: String,
        phone: String
    ) {
        val registerUser = RegisterUser(UserDetailDataModel(email, name, phone), password)
        registerResult.value = BaseResponse.Loading()
        if (registerUser.isEmailValid() && registerUser.isPassword() && registerUser.validName() && registerUser.validPhoneNo()) {
            viewModelScope.launch {
                try {
                    repository.createAccount(registerUser)
                    registerResult.value = BaseResponse.Success()
                } catch (e: Exception) {
                    registerResult.value = BaseResponse.Error(e.message.toString())
                }
            }

        } else {
            if (!registerUser.isEmailValid()) registerResult.value =
                BaseResponse.FormValidationError(
                    FormValidationEnum.email,
                    "Please enter valid email address"
                )
            else if (!registerUser.isPassword())
                registerResult.value =
                    BaseResponse.FormValidationError(
                        FormValidationEnum.password,
                        "Please enter valid password"
                    )
            else if (!registerUser.validName()) registerResult.value =
                BaseResponse.FormValidationError(
                    FormValidationEnum.username,
                    "Please enter valid name"
                )
            else if (!registerUser.validPhoneNo()) registerResult.value =
                BaseResponse.FormValidationError(
                    FormValidationEnum.phone,
                    "Please enter valid phone number"
                )
            else registerResult.value = BaseResponse.Error("Something went wrong")
        }
    }

    fun onLoginAction(email: String, password: String) {
        val loginUser = LoginUser(email, password)
        loginResult.value = BaseResponse.Loading()
        if (loginUser.isEmailValid() && loginUser.isPassword()) {
            viewModelScope.launch {
                try {
                    repository.login(loginUser)
                    loginResult.value = BaseResponse.Success()
                } catch (e: Exception) {
                    loginResult.value = BaseResponse.Error(e.message.toString())
                }
            }

        } else {
            loginResult.value = BaseResponse.Error("Please enter email id and password")
        }

    }

    fun updateUser(name: String, phone: String) {
        updateProfileResult.value = BaseResponse.Loading()
        val user = UpdateUserDetailModel(name, phone)
        if (user.validName() && user.validPhoneNo() && observeUserDataLiveDataResult.value != null) {
            viewModelScope.launch {
                try {
                    repository.updateUser(observeUserDataLiveDataResult.value?.uid!!, user)
                    updateProfileResult.value = BaseResponse.Success()
                    val userData = observeUserDataLiveDataResult.value
                    userMutableLiveData.value =
                        userData?.copyData(userData.data.copy(user.name, user.phone))
                } catch (e: Exception) {
                    updateProfileResult.value = BaseResponse.Error(e.message.toString())
                }
            }
        } else {
            if (!user.validName()) {
                updateProfileResult.value = BaseResponse.FormValidationError(
                    FormValidationEnum.username,
                    if (user.name.isEmpty()) "Please enter your name" else "name is not valid"
                )
            }
            if (!user.validPhoneNo()) {
                updateProfileResult.value = BaseResponse.FormValidationError(
                    FormValidationEnum.phone,
                    "Please enter valid phone no"
                )
            }
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            try {
                val data = repository.getUserData()
                userMutableLiveData.value = data
            } catch (e: Exception) {
                AppUtils.print("getUserData ::" + e.message.toString())
            }
        }
    }

    fun isAuth(): Boolean {
        try {
            return repository.isAuth()
        } catch (e: Exception) {
            throw e
        }
    }

    fun observeLoginLiveDataResult(): LiveData<BaseResponse<UserDataModel<UserDetailDataModel>>> =
        loginResult


    fun observeSignUpLiveDataResult(): LiveData<BaseResponse<UserDataModel<UserDetailDataModel>>> =
        registerResult


    val observeUserDataLiveDataResult: LiveData<UserDataModel<UserDetailDataModel>?>
        get() = userMutableLiveData

    val observeProfileDataLiveDataResult: LiveData<BaseResponse<Boolean>>
        get() = updateProfileResult

}