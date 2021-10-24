package com.sourabhverma.stocksimulator.report

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseAdapter

class ScreenShotAdapter : BaseAdapter() {

    private var listOfBitmap : MutableList<Bitmap?> = mutableListOf()
    private lateinit var context: Context
    private lateinit var listener: ClickListener

    fun setListOfBitmap(bitmapArray: MutableList<Bitmap?>, context : Context, listener: ClickListener){
        this.listOfBitmap = bitmapArray
        this.context = context
        this.listener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (listOfBitmap.isNotEmpty() && position<listOfBitmap.size && listOfBitmap[position] != null) {
            holder as ScreenShotViewHolder
            val layoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            layoutParams.dimensionRatio = "${displayMetrics.widthPixels}:${displayMetrics.heightPixels}"
            holder.imageView.layoutParams = layoutParams
            holder.imageView.setImageBitmap(listOfBitmap[position])
            holder.imageView.setOnClickListener {
                listener.removeThis(position)
            }
            holder.remove.setOnClickListener {
                listener.removeThis(position)
            }
        } else {
            holder as AddMoreScreenShot
            val layoutParams = holder.imageView.layoutParams as ConstraintLayout.LayoutParams
            val displayMetrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            layoutParams.dimensionRatio = "${displayMetrics.widthPixels}:${displayMetrics.heightPixels}"
            holder.imageView.layoutParams = layoutParams
            holder.root.setOnClickListener{
                listener.addMoreScreenShot()
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
        var remove : ImageView = view.findViewById(R.id.remove_screen_shot_button)
    }

    class AddMoreScreenShot(view: View) : RecyclerView.ViewHolder(view){
        var root : ConstraintLayout = view.findViewById(R.id.root)
        var imageView : ImageView = view.findViewById(R.id.screen_shot)
    }

}