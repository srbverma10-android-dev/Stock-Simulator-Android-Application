package com.sourabhverma.stocksimulator.main_activity

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.sourabhverma.stocksimulator.BuildConfig
import com.sourabhverma.stocksimulator.data.Indices
import com.sourabhverma.stocksimulator.data.IndicesDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.collections.HashMap

class MainActivityRepo {

    private val mExecutor: Executor = Executors.newSingleThreadExecutor()

    private val allIndUrl :String = BuildConfig.REST_HOST + "indices"

    private fun createHttpTask(u:String, jsonObjectString : String? = null, isPost : Boolean = false): Task<String> {
        return Tasks.call(mExecutor, Callable {
            val url = URL(u)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.doOutput = true
            if (!isPost) {
                conn.requestMethod = "GET"
            } else {
                conn.requestMethod = "POST"
            }
            conn.connectTimeout = 30000
            conn.readTimeout = 30000
            val headers: MutableMap<String, String> = HashMap()
            headers["content-type"] = "multipart/form-data; boundary=" + UUID.randomUUID().toString()
            headers["accept"] = "*/*"
            for (headerKey in headers.keys) {
                conn.setRequestProperty(headerKey, headers[headerKey])
            }
            val os: OutputStream = conn.outputStream
            val osw = OutputStreamWriter(os, "UTF-8")

            osw.append("--" + UUID.randomUUID().toString()).append("\r\n")
            osw.append("Content-Disposition: form-data; name=\"" + "symbol" + "\"").append("\r\n")
            osw.append("Content-Type: text/plain; charset=" + "utf-8").append("\r\n")
            osw.append("\r\n")
            osw.append(jsonObjectString?.trim()).append("\r\n")
            osw.flush()
            osw.close()
            os.close()
            conn.connect()
            val rc = conn.responseCode
            if (rc != HttpURLConnection.HTTP_OK) {
                throw Exception("Error: $rc")
            }
            val inp: InputStream = BufferedInputStream(conn.inputStream)
            return@Callable inp.bufferedReader(UTF_8).use { it.readText() }
        })
    }

    fun getIndices(symbol : String, indicesDAO: IndicesDAO){
        createHttpTask(allIndUrl, symbol, true)
            .addOnSuccessListener {
                val json = JSONObject(it)
                GlobalScope.launch {
                    indicesDAO.insert(Indices(change = json.getString("change").toDouble(),
                        name = json.getString("name"),
                        symbol = json.getString("symbol"),
                        high = json.getString("high").toDouble(),
                        low = json.getString("low").toDouble(),
                        current = json.getString("current").toDouble(),
                        graphData = json.getJSONArray("graphData").toString(),
                        hasNext = json.getString("hasNext").toBoolean(),
                        code = json.getString("code").toInt(),
                        uid = json.getInt("uid")))
                }
            }
            .addOnFailureListener {
            }
    }

}