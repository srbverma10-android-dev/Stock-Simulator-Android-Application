package com.sourabhverma.stocksimulator.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import org.json.JSONArray
import kotlin.math.abs

class LineChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle){

    private var path : Path = Path()
    private var pathForShadow : Path = Path()
    private var linePaint: Paint = Paint()
    private var effect = CornerPathEffect(48f)

    private lateinit var jsonArray: JSONArray

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        linePaint.flags = Paint.ANTI_ALIAS_FLAG
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeCap = Paint.Cap.ROUND

        val spaceX = (width.toFloat()/(jsonArray.length()+1).toFloat())
        var min = Float.MAX_VALUE
        var max = 0f

        for (i in 0 until jsonArray.length()){
            if(jsonArray.getJSONArray(i).get(1).toString().toFloat() < min){
                min = jsonArray.getJSONArray(i).get(1).toString().toFloat()
            }
            if(jsonArray.getJSONArray(i).get(1).toString().toFloat() > max){
                max = jsonArray.getJSONArray(i).get(1).toString().toFloat()
            }
        }
        var x = spaceX
        var y = abs(height- normalize(jsonArray.getJSONArray(0).get(1).toString().toFloat(), max,min))

        path.moveTo(x,y)
        pathForShadow.moveTo(x, y)
        for (i in 1 until jsonArray.length()){
            x += spaceX
            y = normalize(jsonArray.getJSONArray(i).get(1).toString().toFloat(), max, min)
            path.lineTo(x, height-y)
            pathForShadow.lineTo(x, height-y+8f)
        }
        linePaint.pathEffect = effect
        linePaint.strokeWidth = 12f
        linePaint.color = Color.parseColor("#992C3E50")
        canvas?.drawPath(pathForShadow, linePaint)
        linePaint.color = Color.parseColor("#FFE7E0C9")
        linePaint.strokeWidth = 8f
        canvas?.drawPath(path, linePaint)

    }

    private fun normalize(value : Float, max : Float, min : Float) : Float{
        val newMax = height-27f
        val newMin = 27f
        return ((value - min) / (max - min) ) * (newMax - newMin) + newMin
    }

    fun setJson(array : JSONArray){
        this.jsonArray = array
    }


}