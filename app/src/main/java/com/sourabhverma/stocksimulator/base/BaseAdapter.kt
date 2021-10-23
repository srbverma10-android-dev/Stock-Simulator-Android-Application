package com.sourabhverma.stocksimulator.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType==0) {
            val view: View =
                LayoutInflater.from(parent.context).inflate(getLayoutId()[0], parent, false)
            getViewHolder(view, viewType)
        } else {
            val view: View =
                LayoutInflater.from(parent.context).inflate(getLayoutId()[1], parent, false)
            getViewHolder(view, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return itemCount()
    }

    abstract fun getLayoutId() : List<Int>

    abstract fun getViewHolder(view:View, viewType : Int) : RecyclerView.ViewHolder

    abstract fun itemCount() : Int

    abstract fun getViewType(position:Int) : Int

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }
}