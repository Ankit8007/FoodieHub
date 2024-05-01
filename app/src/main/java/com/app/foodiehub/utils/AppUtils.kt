package com.app.foodiehub.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.app.foodiehub.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AppUtils {
    companion object {
        fun setDelay(seconds: Long, callBack: () -> Unit) {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(callBack, seconds)
        }

        fun print(msg: String) = Log.d("<DEBUG>", msg)


        fun toast(context: Context, msg: String) =
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()


        fun showLoader(context: Context): Dialog {
            val dialog = Dialog(context)
            dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.layout_progressbar)

            return dialog
        }


        fun cartDialog(context: Context,okBtnAction:()->Unit): Dialog {
            val dialog = Dialog(context)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.layout_cart_dialog)
            val btn = dialog.findViewById<Button>(R.id.okBtn)
            btn.setOnClickListener { okBtnAction()}
            return dialog
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val capabilities =
                    connectivityManager.getNetworkCapabilities(network) ?: return false
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
        }

        fun uiThread(launch: () -> Unit) {
            Thread {
                launch()
            }.start()
        }

        fun bgThreadFragment(owner: LifecycleOwner, launch: () -> Unit) {
            owner.lifecycleScope.launch {
                delay(200)
                launch()
            }
        }
    }
}