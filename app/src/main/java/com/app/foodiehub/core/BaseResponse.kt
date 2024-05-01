package com.app.foodiehub.core

import com.app.foodiehub.utils.FormValidationEnum

sealed class BaseResponse<out T> {
    data class Success<out T>(val data: T? = null) : BaseResponse<T>()
    data class Loading(val nothing: Nothing?=null) : BaseResponse<Nothing>()
    data class Error(val msg: String?) : BaseResponse<Nothing>()
    data class FormValidationError(val key: FormValidationEnum,val msg: String?) : BaseResponse<Nothing>()
}