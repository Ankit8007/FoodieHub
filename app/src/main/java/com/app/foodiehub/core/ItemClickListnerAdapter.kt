package com.app.foodiehub.core

interface ItemClickListenerAdapter<T> {
    fun onItemClick( value: T): Void
}