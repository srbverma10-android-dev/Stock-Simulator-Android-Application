package com.sourabhverma.stocksimulator.report

import android.content.Context
import android.graphics.Bitmap
import com.sourabhverma.stocksimulator.data.ApiHandler
import com.sourabhverma.stocksimulator.data.GeneralResponseModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class ReportRepo {

    private val apiHandler: ApiHandler = ApiHandler()

    fun addReport(email: String, type: String, secondParam: String, screenShot: MutableList<Bitmap?>, logCsvFile: File, build: String, context: Context, onResult: (GeneralResponseModel?)->Unit){
        val listOfFile : MutableList<MultipartBody.Part> = mutableListOf()
        var i = 0
        for (s in screenShot){
            val tempFile = s?.let { bitmapToFile(context, it, i.toString()) }
            if (tempFile != null) {
                val screenShotPart = MultipartBody.Part.createFormData(
                    "screenShot", "$i.png", RequestBody.create(
                        MediaType.parse("image/png"),
                        tempFile
                    )
                )
                listOfFile.add(screenShotPart)
                i += 1
            }
        }
        val logCsvPart = MultipartBody.Part.createFormData(
            "logCsvFile", "1.csv", RequestBody.create(
                MediaType.parse("text/csv"),
                logCsvFile
            )
        )

        val json = JSONObject(build)
        apiHandler.addReport(email, type, secondParam, listOfFile, logCsvPart, json)?.enqueue(object : Callback<GeneralResponseModel?>{
            override fun onResponse(
                call: Call<GeneralResponseModel?>,
                response: Response<GeneralResponseModel?>
            ) {
                if (response.code() == 200){
                    onResult(response.body())
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<GeneralResponseModel?>, t: Throwable) {
                onResult(null)
            }

        })
    }

    private fun bitmapToFile(
        context: Context,
        bitmap: Bitmap,
        fileNameToSave: String
    ): File {
        val file: File?
        file = File(context.cacheDir.toString() + File.separator + fileNameToSave)
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val bitmapdata: ByteArray = bos.toByteArray()

        val fos = FileOutputStream(file)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        return file
    }

}