package com.sourabhverma.stocksimulator.report

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseAdapter


class ScreenShotAdapter : BaseAdapter() {

    private var listOfBitmap : Array<Bitmap?> = emptyArray()
    private lateinit var context: Context

    fun setListOfBitmap(bitmapArray: Array<Bitmap?>, context : Context){
        this.listOfBitmap = bitmapArray
        this.context = context
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (listOfBitmap.isNotEmpty() && position<listOfBitmap.size && listOfBitmap[position] != null) {
            holder as ScreenShotViewHolder
            val layoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            layoutParams.dimensionRatio = "${displayMetrics.widthPixels}:${displayMetrics.heightPixels}"
            Log.d("SOURABHVERMAADAPTER", "onBindViewHolder: in if ${displayMetrics.widthPixels} ${displayMetrics.heightPixels}")
            holder.imageView.layoutParams = layoutParams
            holder.imageView.setImageBitmap(listOfBitmap[position])
        } else {
            holder as AddMoreScreenShot
            val layoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            layoutParams.dimensionRatio = "${displayMetrics.widthPixels}:${displayMetrics.heightPixels}"
            holder.imageView.layoutParams = layoutParams
            holder.root.setOnClickListener{
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getLayoutId(): List<Int> = listOf(R.layout.screen_shot_list,R.layout.add_more_screen_shot)

    override fun getViewHolder(view: View, viewType : Int): RecyclerView.ViewHolder {
        return if (viewType == 0){
            ScreenShotViewHolder(view)
        } else {
            AddMoreScreenShot(view)
        }
    }

    override fun itemCount(): Int = (listOfBitmap.size+1)

    override fun getViewType(position: Int): Int {
        return if (position == listOfBitmap.size){
            1
        } else {
            0
        }
    }

    class ScreenShotViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imageView : ImageView = view.findViewById(R.id.screen_shot)
    }

    class AddMoreScreenShot(view: View) : RecyclerView.ViewHolder(view){
        var root : ConstraintLayout = view.findViewById(R.id.root)
        var imageView : ImageView = view.findViewById(R.id.screen_shot)
    }

}