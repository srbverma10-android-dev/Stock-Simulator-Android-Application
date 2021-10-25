package com.sourabhverma.stocksimulator.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.sourabhverma.stocksimulator.R

class RoundCornersImageView : AppCompatImageView {
    enum class TYPE {
        RECTANGLE, CIRCLE;

        companion object {
            fun fromInteger(x: Int):TYPE? {
                when (x) {
                    0 -> return RECTANGLE
                    1 -> return CIRCLE
                }
                return null
            }
        }
    }

    private var path: Path? = null
    private var cornerRadius = 0f
    private var type:TYPE =
        TYPE.RECTANGLE
    private var borderPaint: Paint? = null

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
        fetchAttributes(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
        fetchAttributes(context, attrs, defStyleAttr)
    }

    private fun init() {
        borderPaint = Paint()
        borderPaint!!.style = Paint.Style.STROKE
        borderPaint!!.color = Color.TRANSPARENT
        borderPaint!!.strokeWidth = 0f
        borderPaint!!.isAntiAlias = true
        scaleType = ScaleType.CENTER_CROP
    }

    private fun fetchAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RoundCornersImageView,
            defStyleAttr,
            0
        )
        try {
            cornerRadius = typedArray.getDimension(R.styleable.RoundCornersImageView_corners, 0f)
            type = TYPE.fromInteger(
                typedArray.getInt(
                    R.styleable.RoundCornersImageView_type,
                    0
                )
            )!!
            borderPaint!!.color =
                typedArray.getColor(
                    R.styleable.RoundCornersImageView_borderColor,
                    Color.parseColor("#00000000")
                )
            borderPaint!!.strokeWidth =
                typedArray.getDimension(R.styleable.RoundCornersImageView_borderThickness, 0f)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val rectF = RectF(0f, 0f, w.toFloat(), h.toFloat())
        path = Path()
        if (type == TYPE.CIRCLE) {
            val radius: Int = if (w < h) {
                w shr 1
            } else {
                h shr 1
            }
            path!!.addCircle(
                (w shr 1).toFloat(),
                (h shr 1).toFloat(),
                radius.toFloat(),
                Path.Direction.CW
            )
        } else {
            path!!.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)
        }
        path!!.close()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.clipPath(path!!)
        super.onDraw(canvas)
        canvas.drawPath(path!!, borderPaint!!)
        canvas.restore()
    }
}