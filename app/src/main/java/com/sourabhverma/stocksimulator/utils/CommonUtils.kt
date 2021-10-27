package com.sourabhverma.stocksimulator.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.util.Patterns

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

    fun change(context: Context): String? {
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

}