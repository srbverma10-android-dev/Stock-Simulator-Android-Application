package com.sourabhverma.stocksimulator.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPrefManager {
    private val sharedPrefName = "StockSimulator"
    private val username = "USERNAME"
    private val defaultName = "Investor"
    private lateinit var sharedPreferences : SharedPreferences

    fun getUsername(context: Context) : String?{
        sharedPreferences = context.getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        return sharedPreferences.getString(username,defaultName)
    }

}