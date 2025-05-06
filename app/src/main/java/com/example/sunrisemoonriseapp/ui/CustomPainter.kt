package com.example.sunrisemoonriseapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup

@SuppressLint("ViewConstructor")
class CustomPainter(
    context: Context,
    width: Int,
    height: Int,
    private val painter: Painter
) : View(context) {

    init {
        layoutParams = ViewGroup.LayoutParams(width.dpToPx(context), height.dpToPx(context))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.let(painter::paint)
    }
}
fun Int.dpToPx(context: Context): Int = (this * context.resources.displayMetrics.density).toInt()
