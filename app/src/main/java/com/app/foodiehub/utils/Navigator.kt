package com.app.foodiehub.utils

import android.app.Activity
import android.content.Context
import android.content.Intent

class Navigator {
    companion object {
        fun push(context: Context, nextActivity: Class<*>) {
            context.startActivity(Intent(context, nextActivity))
        }

        fun pushAndReplace(context: Context, nextActivity: Class<*>) {
            push(context, nextActivity)
            (context as? Activity)?.finish()
        }
    }
}