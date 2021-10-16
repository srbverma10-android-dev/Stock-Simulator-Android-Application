package com.sourabhverma.stocksimulator.utils

import android.text.TextUtils
import android.util.Patterns

class CommonUtils {

    val fromStr = "FROM"
    val heading = "TEXT_TO_PASS_DEEP_ACTIVITY_HEADING"
    val type = "TEXT_TO_PASS_DEEP_ACTIVITY_TYPE"
    val reportDialogFragmentStr = "REPORT_DIALOG_FRAGMENT"
    val defaultStr = "DEFAULT"

    fun reportDialogTag() : String{
        return "REPORT DIALOG STRING PASS"
    }

    fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}