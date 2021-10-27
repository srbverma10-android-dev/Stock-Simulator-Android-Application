package com.sourabhverma.stocksimulator.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPrefManager {
    private val sharedPrefName = "StockSimulator"
    private val username = "USERNAME"
    private val shouldShowFirstSuccessCard = "SHOULDSHOWFIRSTSUCCESSCARD"
    private val currentAmount = "CURRENTAMOUNT"

    private val defaultName = "Investor"
    private val defaultAmount = "100000000"

    private lateinit var sharedPreferences : SharedPreferences

    fun getUsername(context: Context) : String?{
        sharedPreferences = context.getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        return sharedPreferences.getString(username,defaultName)
    }

    fun getShouldShowFirstSuccessCard(context: Context) : Boolean{
        sharedPreferences = context.getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        return sharedPreferences.getBoolean(shouldShowFirstSuccessCard, true)
    }

    fun setShouldShowFirstSuccessCard(context: Context, boolean: Boolean) : Boolean{
        sharedPreferences = context.getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        return sharedPreferences.edit().putBoolean(shouldShowFirstSuccessCard, boolean).commit()
    }

    fun setCurrentAmount(context: Context, amount : String) : Boolean{
        sharedPreferences = context.getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        return sharedPreferences.edit().putString(currentAmount, amount).commit()
    }

    fun getCurrentAmount(context: Context) : String? {
        sharedPreferences = context.getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        return sharedPreferences.getString(currentAmount, defaultAmount)
    }

}