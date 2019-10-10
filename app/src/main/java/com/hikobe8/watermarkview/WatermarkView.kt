package com.hikobe8.watermarkview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Looper
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import kotlin.math.abs

/**
 * Author : Ray
 * Time : 2019-10-09 17:32
 * Description : text watermark view
 */
class WatermarkView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        const val TAG = "WatermarkView"
    }

    constructor(context: Context?) : this(context, null)

    private val _paint = TextPaint().apply {
        isAntiAlias = true
        textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            14f,
            context!!.resources.displayMetrics
        )
        color = Color.parseColor("#80999999")
    }

    private var _watermarkText = "管理员"
    private var _textWidth = 0f
    private var _textBaseLineY = 0f
    private var _textHeight = 0f
    private val _degree = -15f
    private val _textHorizontalSpacing = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        100f,
        context!!.resources.displayMetrics
    )
    private val _textVerticalSpacing = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        50f,
        context!!.resources.displayMetrics
    )
    //水印文字行数
    private var _rowCount = 0
    //水印文字列数
    private var _colCount = 0

    fun setWatermark(text: String) {
        _watermarkText = text
        if (Looper.myLooper() == Looper.getMainLooper()) {
            invalidate()
        } else {
            postInvalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        _textBaseLineY =
            abs(_paint.fontMetrics.top) - (_paint.fontMetrics.bottom - _paint.fontMetrics.top) / 2f
        _textWidth = _paint.measureText(_watermarkText)
        _textHeight = _paint.fontMetrics.let {
            it.bottom - it.top
        }
        _rowCount = (h / (_textHeight)).toInt()
        _colCount = (w / (_textWidth)).toInt()
        Log.d(TAG, "row = $_rowCount")
        Log.d(TAG, "col = $_colCount")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            save()
            rotate(_degree, width / 2f, height / 2f)
            for (rowIndex in 0.._rowCount) {
                for (colIndex in 0.._colCount) {
                    drawText(
                        _watermarkText,
                        colIndex * (_textWidth + _textHorizontalSpacing) - _textHorizontalSpacing * rowIndex,
                        _textBaseLineY + rowIndex * (_textHeight + _textVerticalSpacing),
                        _paint
                    )
                }
            }
            restore()
        }
    }

}