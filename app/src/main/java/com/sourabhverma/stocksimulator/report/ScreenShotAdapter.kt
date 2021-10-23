package com.sourabhverma.stocksimulator.report

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseAdapter


class ScreenShotAdapter : BaseAdapter() {

    private var listOfBitmap : Array<Bitmap?> = emptyArray()

    fun setListOfBitmap(bitmapArray: Array<Bitmap?>){
        this.listOfBitmap = bitmapArray
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder as ScreenShotViewHolder
        if (listOfBitmap.isNotEmpty() && position<listOfBitmap.size && listOfBitmap[position] != null) {
            val layoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            val width = listOfBitmap[position]?.width!!
            val height = listOfBitmap[position]?.height!!
            layoutParams.dimensionRatio = "$width:$height"
            holder.imageView.layoutParams = layoutParams
            holder.imageView.setImageBitmap(listOfBitmap[position])
        }
    }

    override fun getLayoutId(): Int = R.layout.screen_shot_list

    override fun getViewHolder(view: View): RecyclerView.ViewHolder = ScreenShotViewHolder(view)

    override fun itemCount(): Int = listOfBitmap.size

    class ScreenShotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView : ImageView = itemView.findViewById(R.id.screen_shot)
    }

}