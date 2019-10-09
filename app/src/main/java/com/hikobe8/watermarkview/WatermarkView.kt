package com.hikobe8.watermarkview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import kotlin.math.abs

/**
 * Author : Ray
 * Time : 2019-10-09 17:32
 * Description :
 */
class WatermarkView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    constructor(context: Context?) : this(context, null)

    private val _paint = TextPaint().apply {
        isAntiAlias = true
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, context!!.resources.displayMetrics)
        color = Color.parseColor("#80999999")
    }

    private val _watermarkText = "管理员"
    private var _textWidth = 0f
    private var _textBaseLineY = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _textBaseLineY = abs(_paint.fontMetrics.top) - (_paint.fontMetrics.bottom - _paint.fontMetrics.top)/2f
        _textWidth = _paint.measureText(_watermarkText)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
//            drawRect(RectF(width / 2f - 100, height / 2f - 100 , width / 2f + 100, height / 2f + 100), _paint.apply {
//                color = Color.GREEN
//            })
            drawText(_watermarkText,width / 2f - _textWidth / 2f, height / 2f + _textBaseLineY, _paint)
            save()
            rotate(-15f, width / 2f, height / 2f)
            drawText(_watermarkText, width / 2f - _textWidth / 2f, height / 2f + _textBaseLineY, _paint)
            restore()
        }
    }

}