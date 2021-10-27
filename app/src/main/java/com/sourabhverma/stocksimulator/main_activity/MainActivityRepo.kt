package com.sourabhverma.stocksimulator.main_activity

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

    private fun createHttpTask(u:String): Task<String> {
        return Tasks.call(mExecutor, Callable {
            val url = URL(u)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 3000
            conn.readTimeout = 3000
            val rc = conn.responseCode
            if (rc != HttpURLConnection.HTTP_OK) {
                throw Exception("Error: $rc")
            }
            val inp: InputStream = BufferedInputStream(conn.inputStream)
            return@Callable inp.bufferedReader(UTF_8).use { it.readText() }
        })
    }

    fun getNifty50(onResult: (JSONObject?)->Unit){
        createHttpTask(allIndUrl)
            .addOnSuccessListener {
                val json = JSONObject(it)
                val removeDataHalf = listOf("timestamp", "advances", "declines", "unchanged", "dates", "date30dAgo", "date365dAgo")
                for(r in removeDataHalf){
                    json.remove(r)
                }
                val jsonArray = JSONArray()
                val mainKey = listOf("NIFTY 50", "NIFTY NEXT 50", "NIFTY MIDCAP 50", "NIFTY MIDCAP 100", "NIFTY BANK", "NIFTY AUTO", "NIFTY IT", "NIFTY FINANCIAL SERVICES", "NIFTY MEDIA", "NIFTY PHARMA", "NIFTY HEALTHCARE INDEX")
                for(i in 0 until json.getJSONArray("data").length()){
                    if (mainKey.contains(json.getJSONArray("data").getJSONObject(i).getString("index"))){
                        val removeInData = listOf("indexSymbol", "yearHigh", "yearLow", "pe", "pb", "dy", "declines", "advances", "unchanged", "perChange365d", "date365dAgo", "chart365dPath", "date30dAgo", "perChange30d", "chart30dPath", "chartTodayPath", "previousDay","oneWeekAgo","oneMonthAgo","oneYearAgo")
                        for (r in removeInData){
                            json.getJSONArray("data").getJSONObject(i).remove(r)
                        }
                        jsonArray.put(json.getJSONArray("data").getJSONObject(i))
                    }
                }
                json.remove("data")
                json.put("data", jsonArray)
                onResult(json)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

}