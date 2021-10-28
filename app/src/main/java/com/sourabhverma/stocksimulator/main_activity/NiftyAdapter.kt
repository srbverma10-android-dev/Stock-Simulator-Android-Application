package com.sourabhverma.stocksimulator.main_activity

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseAdapter
import org.json.JSONArray

class NiftyAdapter : BaseAdapter() {

    private var listOfData : JSONArray = JSONArray()
    private lateinit var context : Context

    fun setData(list : JSONArray, context: Context){
        this.listOfData = list
        this.context = context
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder as NiftyViewHolder
        holder.textview.text = listOfData.getJSONObject(position).getString("index")
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
        var textview : TextView = view.findViewById(R.id.textlayout)
    }
}