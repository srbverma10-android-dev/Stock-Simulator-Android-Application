package com.sourabhverma.stocksimulator.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.util.Patterns
import org.json.JSONArray

class CommonUtils {

    val fromStr = "FROM"
    val heading = "TEXT_TO_PASS_DEEP_ACTIVITY_HEADING"
    val type = "TEXT_TO_PASS_DEEP_ACTIVITY_TYPE"
    val reportDialogFragmentStr = "REPORT_DIALOG_FRAGMENT"
    val defaultStr = "DEFAULT"
    val amount = "100000000"

    val lifeCycle = "LIFE-CYCLE"
    val register = "SENSOR-REGISTER"
    val unregister = "SENSOR-UNREGISTER"
    val screenShot = "SCREEN-SHOT"
    val reportDialogFragment = "REPORT-DIALOG-FRAGMENT"
    val permission = "PERMISSION"
    val onClick = "ON-CLICK"
    val passedData = "PASSED-DATA"
    val showError = "SHOW-ERROR"

    val logCsvHeader = "tag,value\n"

    fun isInternetWorking(context: Context) : Boolean{
        val isConnected: Boolean
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        isConnected = when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return isConnected
    }

    fun reportDialogTag() : String{
        return "REPORT DIALOG STRING PASS"
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun changeToAmtIntWithRsIcon(string: String) : String {
        var str = string
        var formatted = "₹ "
        if (str.length > 1) {
            formatted = "₹ " + str.substring(0, 1)
            str = str.substring(1)
        }
        while (str.length > 3) {
            formatted += "," + str.substring(0, 2)
            str = str.substring(2)
        }
        return formatted
    }

    fun changeToAmtInt(context: Context): String? {
        var str = SharedPrefManager().getCurrentAmount(context)
        return if (str?.length != null) {
            var formatted = ""
            if (str.length > 1) {
                formatted = str.substring(0, 1)
                str = str.substring(1)
            }
            while (str?.length!! > 3) {
                formatted += "," + str?.substring(0, 2)
                str = str?.substring(2)
            }
            formatted
        } else {
            SharedPrefManager().getCurrentAmount(context)
        }
    }

    fun returnJsonArray() : JSONArray {
        return JSONArray("[\n" +
                "        [\n" +
                "            1635422908000,\n" +
                "            18049.25\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422909000,\n" +
                "            18048.15\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422910000,\n" +
                "            18047.45\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422911000,\n" +
                "            18049.05\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422912000,\n" +
                "            18049.15\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422913000,\n" +
                "            18049.25\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422914000,\n" +
                "            18048.7\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422915000,\n" +
                "            18048.6\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422916000,\n" +
                "            18049.55\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422917000,\n" +
                "            18047.8\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422918000,\n" +
                "            18047.6\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422919000,\n" +
                "            18047.45\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422920000,\n" +
                "            18048.25\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422921000,\n" +
                "            18048.15\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422922000,\n" +
                "            18046.9\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422923000,\n" +
                "            18047.05\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422924000,\n" +
                "            18046.6\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422925000,\n" +
                "            18047.9\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422926000,\n" +
                "            18048.25\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422927000,\n" +
                "            18047\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422928000,\n" +
                "            18046.6\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422929000,\n" +
                "            18046.35\n" +
                "        ],\n" +
                "        [\n" +
                "            1635422930000,\n" +
                "            18047.1\n" +
                "        ]\n" +
                "    ]")
    }

}