package com.sourabhverma.stocksimulator.main_activity

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseAdapter
import org.json.JSONArray

class TopLooserAdapter : BaseAdapter() {
    private var list : List<JSONArray> = mutableListOf()
    private lateinit var context : Context

    fun setData(list : List<JSONArray>){
        this.list = list
    }

    fun setContext(context: Context){
        this.context = context
    }

    override fun getLayoutId(): List<Int> = listOf(R.layout.top_gainer)

    override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
        return TopLooserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder as TopLooserViewHolder
        holder.symbol.text = list[0].get(position).toString()
        holder.close.text = list[1].get(position).toString()
        holder.change.text = list[2].get(position).toString()
    }

    override fun itemCount(): Int = list.size

    override fun getViewType(position: Int): Int {
        return 0
    }

    class TopLooserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var symbol : TextView = view.findViewById(R.id.symbol)
        var close : TextView = view.findViewById(R.id.close)
        var change : TextView = view.findViewById(R.id.change)
    }

}