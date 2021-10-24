package com.sourabhverma.stocksimulator.data

import com.sourabhverma.stocksimulator.BuildConfig
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiHandler {

    @Multipart
    @POST("report")
    fun addReport(
        @Part("email") email: String,
        @Part("type") type: String,
        @Part("secondParam") secondParam: String,
        @Part screenShot : List<MultipartBody.Part>,
        @Part logCsvFile: MultipartBody.Part,
        @Part("build") build: JSONObject
    ): Call<GeneralResponseModel?>?

    companion object{
        operator fun invoke() : ApiHandler {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.REST_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHandler::class.java)
        }
    }

}