package com.app.foodiehub.presentation.viewModel

class VMScope {
    companion object {
        lateinit var authViewModel: AuthViewModel
        lateinit var foodViewModel: FoodViewModel

        fun injectAuth(authViewModel: AuthViewModel){
            this.authViewModel = authViewModel
        }

        fun injectFood(foodViewModel: FoodViewModel){
            this.foodViewModel = foodViewModel
        }
    }
}