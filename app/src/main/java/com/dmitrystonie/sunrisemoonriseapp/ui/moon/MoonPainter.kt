package com.dmitrystonie.sunrisemoonriseapp.ui.moon

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.ColorInt

class MoonPainter(@ColorInt private val baseColor: Int, @ColorInt private val subColor: Int, private val leftWidth: Float = -1F, private val rightWidth: Float = -1F): Painter {
    override fun paint(canvas: Canvas) {
        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()
        val basePaint = Paint()
        basePaint.color = baseColor
        val subPaint = Paint()
        subPaint.color = subColor
        val rectLeft = if(leftWidth == -1F) RectF(0F, 0F, width, height) else RectF(width*0.5F - leftWidth/2*width, 0F, width*0.5F + leftWidth/2*width, height)
        val rectRight = if(rightWidth == -1F) RectF(0F, 0F, width, height) else RectF(width*0.5F - rightWidth/2*width, 0F, width*0.5F + rightWidth/2*width, height)
        canvas.drawCircle(width / 2, height / 2, width / 2, basePaint)
        canvas.drawArc(rectLeft, -90F, 180F, false, subPaint)
        canvas.drawArc(rectRight, 90F, 180F, false, subPaint)
    }
}