package com.angelon.heartdemo.view

import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.floor
import kotlin.math.roundToInt

@Suppress("DEPRECATION")
object MyUtil {
    private const val circle = 2 * Math.PI
    fun rgba(r: Int, g: Int, b: Int, a: Int): Int {
        return Color.argb(a, r, g, b)
    }

    fun randomInt(min: Int, max: Int): Int {
        return (floor(Math.random() * (max - min + 1)) + min).roundToInt()
    }

    fun random(min: Double, max: Double): Double {
        return floor(Math.random() * (max - min + 1)) + min
    }

    //产生随机的argb颜色
    fun randomRgba(rMin: Int, rMax: Int, gMin: Int, gMax: Int, bMin: Int, bMax: Int, a: Int): Int {
        val r = Math.round(random(rMin.toDouble(), rMax.toDouble())).toInt()
        val g = Math.round(random(gMin.toDouble(), gMax.toDouble())).toInt()
        val b = Math.round(random(bMin.toDouble(), bMax.toDouble())).toInt()
        val limit = 5
        return if (Math.abs(r - g) <= limit && Math.abs(g - b) <= limit && Math.abs(b - r) <= limit) {
            rgba(rMin, rMax, gMin, gMax)
        } else {
            rgba(r, g, b, a)
        }
    }

    //调度转弧度
    fun degrad(angle: Double): Double {
        return circle / 360.0 * angle
    }

    fun getScreenWidth(context: Context): Int {
        val manager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        val manager = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}
