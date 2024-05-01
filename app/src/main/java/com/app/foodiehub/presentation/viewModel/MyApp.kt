package com.app.foodiehub.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class MyApp : Application(), ViewModelStoreOwner {

//    private val appViewModelStore: ViewModelStore by lazy {
//        ViewModelStore()
//    }
//
//    override  fun getViewModelStore(): ViewModelStore {
//        return appViewModelStore
//    }
//
//    override val viewModelStore: ViewModelStore
//        get() = appViewModelStore


    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }
    override val viewModelStore: ViewModelStore
        get() = appViewModelStore

//    override fun getViewModelStore(): ViewModelStore {
//        return appViewModelStore
//    }
}