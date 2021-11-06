package com.sourabhverma.stocksimulator.main_activity

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseAdapter
import com.sourabhverma.stocksimulator.ui.LineChart
import com.sourabhverma.stocksimulator.utils.CommonUtils
import org.json.JSONArray
import org.json.JSONObject

class NiftyAdapter : BaseAdapter() {

    private var listOfData : JSONArray = JSONArray()
    private lateinit var context : Context

    fun addJsonObject(jsonObject: JSONObject){
        this.listOfData.put(jsonObject)
    }

    fun setContext(context: Context){
        this.context = context
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder as NiftyViewHolder
        holder.setIsRecyclable(false)
        holder.indexName.text = listOfData.getJSONObject(position).getString("name")
        holder.currentVal.text = CommonUtils().changeToAmtIntWithRsIcon(listOfData.getJSONObject(position).getString("current"))
//        val change = listOfData.getJSONObject(position).getString("percentChange") + "%"
//        holder.changeVal.text = change
        holder.highVal.text = CommonUtils().changeToAmtIntWithRsIcon(listOfData.getJSONObject(position).getString("high"))
        holder.lowVal.text = CommonUtils().changeToAmtIntWithRsIcon(listOfData.getJSONObject(position).getString("low"))
        holder.lineChart.setArray(filterArray(listOfData.getJSONObject(position).getJSONArray("graphData")))
    }
    private fun filterArray(array: JSONArray) : MutableList<Float>{
        val jsonArray = mutableListOf<Float>()
        for(i in 1 until array.length()){
            if (array.get(i-1).toString().toFloat() != array.get(i).toString().toFloat()){
                jsonArray.add(array.get(i).toString().toFloat())
            }
        }
        return jsonArray
    }
    override fun getLayoutId(): List<Int> = listOf(R.layout.nifty_adapter)

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return NiftyViewHolder(view)
    }

    override fun itemCount(): Int = listOfData.length()

    override fun getViewType(position: Int): Int {
        return 0
    }

    class NiftyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var indexName : TextView = view.findViewById(R.id.index_name)
        var changeVal : TextView = view.findViewById(R.id.change_val)
        var highVal : TextView = view.findViewById(R.id.high_val)
        var lowVal : TextView = view.findViewById(R.id.low_val)
        var currentVal : TextView = view.findViewById(R.id.current_val)
        var lineChart : LineChart = view.findViewById(R.id.lineChart)
    }
}