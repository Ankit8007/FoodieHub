package com.app.foodiehub.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.app.foodiehub.R
import com.app.foodiehub.presentation.viewModel.AuthViewModel
import com.app.foodiehub.utils.AppUtils
import com.app.foodiehub.utils.Navigator

class MainActivity : ComponentActivity() {
    private fun nextScreen() {
        AppUtils.setDelay(3000) {
            if (viewModel.isAuth()) Navigator.pushAndReplace(this, HomeActivity::class.java)
            else Navigator.pushAndReplace(this, LoginActivity::class.java)
        }
    }

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nextScreen()
    }
}

