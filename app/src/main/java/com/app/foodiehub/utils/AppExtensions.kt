package com.app.foodiehub.utils

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val pattern = Regex("^\\d{6,14}$")
val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex()
fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPasswordValid(): Boolean {
    return !TextUtils.isEmpty(this) && passwordPattern.matches(this)
}

fun String.isNameValid(): Boolean {
    return !TextUtils.isEmpty(this)
}

fun String.isPhoneValid(): Boolean {
    return !TextUtils.isEmpty(this) && pattern.matches(this)
}

fun Context.toast(msg: String): Unit {
    AppUtils.toast(this,msg)
}

val EditText.getValue: String  get() = this.text.toString().trim()

fun ImageView.loadUrl(url: String) {
    Glide.with(this.context)
        .load(url)
        .override(250, 250)
        .into(this)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

val gson = Gson()


fun List<*>.toJson():String{
    return  gson.toJson(this)
}

inline fun <reified T> String.toList(): List<T> {
    val typeToken = object : TypeToken<List<T>>() {}.type
    return gson.fromJson(this, typeToken)
}

//convert a data class to a map
fun <T> T.serializeToMap(): Map<String, Any> {
    return convert()
}

//convert a map to a data class
inline fun <reified T> Map<String, Any>.toDataClass(): T {
    return convert()
}

//convert an object of type I to type O
inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}