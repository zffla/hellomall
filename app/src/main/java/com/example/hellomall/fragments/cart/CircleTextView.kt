package com.example.hellomall.fragments.cart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import kotlin.math.max

class CircleTextView @JvmOverloads constructor( context: Context, attrs:AttributeSet?=null): TextView(context, attrs) {

    private val mPaint=Paint()

    init {
        mPaint.color=Color.WHITE
        mPaint.isAntiAlias=true
    }

    fun setCircleColor(color:Int){
        mPaint.color=color
        mPaint.style=Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width=MeasureSpec.getSize(widthMeasureSpec)
        val height=MeasureSpec.getSize(heightMeasureSpec)
        val maxSize= max(width, height)
        setMeasuredDimension(maxSize,maxSize)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(width/2.toFloat(),height/2.toFloat(), max(width/2,height/2).toFloat(),mPaint)
        super.onDraw(canvas)
    }
}