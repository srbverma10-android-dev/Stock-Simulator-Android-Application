package com.sourabhverma.stocksimulator.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*

object CacheHelperClass {
    fun getImage(context: Context, position: Int): Bitmap? {
        val fileName = context.cacheDir.toString() + "/" + position
        val file = File(fileName)
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun putImage(context: Context, file_name: String, bitmap: Bitmap) {
        val fileName = context.cacheDir.toString() + "/" + file_name + ".png"
        val file = File(fileName)
        var fileOutputStream: FileOutputStream? = null
        try {
            fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}