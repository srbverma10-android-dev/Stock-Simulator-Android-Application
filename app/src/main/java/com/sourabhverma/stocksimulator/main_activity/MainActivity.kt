package com.sourabhverma.stocksimulator.main_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sourabhverma.stocksimulator.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_StockSimulator)
        setContentView(R.layout.activity_main)
    }
}