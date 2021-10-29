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

    var path : Path = Path()
    private var linePaint: Paint = Paint()
    private var effect = CornerPathEffect(20f)

    private lateinit var jsonArray: JSONArray

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        linePaint.flags = Paint.ANTI_ALIAS_FLAG
        linePaint.color = Color.parseColor("#FFE7E0C9")
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 8f
        linePaint.strokeCap = Paint.Cap.ROUND

        val spaceX = width/(jsonArray.length()+1)
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

        var x = spaceX.toFloat()
        var y = abs(height- normalize(jsonArray.getJSONArray(0).get(1).toString().toFloat(), max,min))

        path.moveTo(x,y)
        for (i in 1 until jsonArray.length()){
            x += spaceX
            y = normalize(jsonArray.getJSONArray(i).get(1).toString().toFloat(), max, min)
            path.lineTo(x, height-y)
        }
        linePaint.pathEffect = effect
        canvas?.drawPath(path, linePaint)
    }

    private fun normalize(value : Float, max : Float, min : Float) : Float{
        return (height * ((value-min)/(max-min)))
    }

    fun setJson(array : JSONArray){
        this.jsonArray = array
    }


}