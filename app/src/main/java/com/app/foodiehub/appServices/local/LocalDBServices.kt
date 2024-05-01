package com.app.foodiehub.appServices.local

import android.content.Context
import android.content.SharedPreferences
import com.app.foodiehub.presentation.model.CartModel
import com.app.foodiehub.utils.toJson
import com.app.foodiehub.utils.toList


class LocalDBServices {
    companion object {
        private lateinit var context: Context
        private lateinit var sharedPreferences: SharedPreferences
        fun setContext(context: Context){
            this.context = context
            sharedPreferences  = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        }

        fun addCart(list : List<CartModel>){
            val editor = sharedPreferences.edit()
            editor.putString("cartList",list.toJson())
            editor.apply()
        }

        fun getCart():List<CartModel>{
            val list = sharedPreferences.getString("cartList", "[]")
           return list?.toList<CartModel>() ?: emptyList()
        }
    }
}