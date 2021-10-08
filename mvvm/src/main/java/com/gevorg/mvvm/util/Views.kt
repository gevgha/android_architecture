package com.gevorg.mvvm.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.gevorg.mvvm.R

fun EditText.toText():String{
    return this.text.toString()
}

fun EditText.toLength():Int{
    return this.text.toString().length
}

private var toast: Toast? = null

fun String.toast(context: Context?) {
    toast?.cancel()
    val layout = LayoutInflater.from(context).inflate(
        R.layout.toast_layout,null)
    toast = Toast.makeText(context, this, Toast.LENGTH_SHORT)
    toast?.setGravity(Gravity.TOP, 0, 25)
    layout.findViewById<TextView>(R.id.toastTextTv).text = this
    layout.findViewById<TextView>(R.id.toastTextTv).setTextColor(ContextCompat.getColor(context!!,R.color.green))
    toast?.view = layout
    toast?.show()
}


fun Int.toast(context: Context?) {
    toast?.cancel()
    val layout = LayoutInflater.from(context).inflate(
        R.layout.toast_layout,null)
    toast = Toast.makeText(context, this, Toast.LENGTH_SHORT)
    toast?.setGravity(Gravity.TOP, 0, 25)
    layout.findViewById<TextView>(R.id.toastTextTv).setText(this)
    layout.findViewById<TextView>(R.id.toastTextTv).setTextColor(ContextCompat.getColor(context!!,R.color.green))
    toast?.view = layout
    toast?.show()
}

object KeyboardUtils {
    fun hide(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isActive) {
            val windowToken: IBinder =
                activity.window.decorView.rootView.windowToken
            inputMethodManager.hideSoftInputFromWindow(
                windowToken, 0
            )
        }
    }

    fun show(activity: Activity, view: EditText) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun show(context: Context, view: EditText) {
        val inputMethodManager: InputMethodManager = context.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

fun isInternetAvailable(context: Context?): Boolean {
    var result = false
    val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    cm?.run {
        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
            result = when {
                hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }
    return result
}