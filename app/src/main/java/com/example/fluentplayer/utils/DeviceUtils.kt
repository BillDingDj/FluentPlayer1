package com.example.fluentplayer.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object DeviceUtils {

    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    fun getStatusBarHeight(context: Context): Int{
        val resource = context.resources
        val resourceId = resource.getIdentifier("status_bar_height", "dimen", "android")
        return resource.getDimensionPixelSize(resourceId)
    }
}

fun Int.dp2px(context: Context) : Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}