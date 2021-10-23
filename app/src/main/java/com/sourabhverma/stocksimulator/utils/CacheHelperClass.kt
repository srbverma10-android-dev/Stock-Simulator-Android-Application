package com.sourabhverma.stocksimulator.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*

object CacheHelperClass {
    fun getImage(context: Context, file_name: String): Bitmap? {
        val fileName = context.cacheDir.toString() + "/" + file_name + ".png"
        val file = File(fileName)
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun getLogFile(context: Context) : File {
        val fileName = context.cacheDir.toString() + "/logFile.csv"
        val file = File(fileName)
        if (!file.exists()) {
            file.appendText(CommonUtils().logCsvHeader)
        }
        return file
    }

    fun resetLogFile(context: Context){
        val fileName = context.cacheDir.toString() + "/logFile.csv"
        val file = File(fileName)
        file.writeText("")
        file.appendText(CommonUtils().logCsvHeader)
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