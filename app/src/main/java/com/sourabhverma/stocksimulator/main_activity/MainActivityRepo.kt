package com.sourabhverma.stocksimulator.main_activity

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets.UTF_8
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivityRepo {

    private val mExecutor: Executor = Executors.newSingleThreadExecutor()

    private val allIndUrl :String = "https://www.nseindia.com/api/allIndices"
    private val graphData : String = "https://www.nseindia.com/api/chart-databyindex?index=[&&&]&indices=true"

    private fun createHttpTask(u:String): Task<String> {
        return Tasks.call(mExecutor, Callable {
            val url = URL(u)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 30000
            conn.readTimeout = 30000
            val headers: MutableMap<String, String> = HashMap()
            headers["X-CSRF-Token"] = "fetch"
            headers["content-type"] = "application/json"
            headers["user-agent"] = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
            for (headerKey in headers.keys) {
                conn.setRequestProperty(headerKey, headers[headerKey])
            }
            val rc = conn.responseCode
            if (rc != HttpURLConnection.HTTP_OK) {
                throw Exception("Error: $rc")
            }
            val inp: InputStream = BufferedInputStream(conn.inputStream)
            return@Callable inp.bufferedReader(UTF_8).use { it.readText() }
        })
    }

    fun getNiftyGraphData(name : String, onResult: (JSONObject?)->Unit){
        createHttpTask(graphData.replace("[&&&]", name))
            .addOnSuccessListener {
                val json = JSONObject(it)
                json.remove("identifier")
                json.remove("closePrice")
                onResult(json)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
    fun getIndices(onResult: (JSONArray?)->Unit){
        createHttpTask(allIndUrl)
            .addOnSuccessListener {
                val json = JSONObject(it)
                val jsonArray = JSONArray()
                val mainKey = listOf("NIFTY 50", "NIFTY NEXT 50", "NIFTY MIDCAP 50", "NIFTY MIDCAP 100", "NIFTY BANK", "NIFTY AUTO", "NIFTY IT", "NIFTY FINANCIAL SERVICES", "NIFTY MEDIA", "NIFTY PHARMA", "NIFTY HEALTHCARE INDEX")
                for(i in 0 until json.getJSONArray("data").length()){
                    if (mainKey.contains(json.getJSONArray("data").getJSONObject(i).getString("index"))){
                        val removeInData = listOf("yearHigh", "yearLow", "pe", "pb", "dy", "declines", "advances", "unchanged", "perChange365d", "date365dAgo", "chart365dPath", "date30dAgo", "perChange30d", "chart30dPath", "chartTodayPath", "previousDay","oneWeekAgo","oneMonthAgo","oneYearAgo")
                        for (r in removeInData){
                            json.getJSONArray("data").getJSONObject(i).remove(r)
                        }
                        jsonArray.put(json.getJSONArray("data").getJSONObject(i))
                    }
                }
                onResult(jsonArray)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

}