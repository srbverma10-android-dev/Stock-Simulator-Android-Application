package com.sourabhverma.stocksimulator.main_activity

import android.os.Bundle
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseActivity
import com.sourabhverma.stocksimulator.databinding.ActivityMainBinding
import com.sourabhverma.stocksimulator.utils.CacheHelperClass

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): Class<MainActivityViewModel> = MainActivityViewModel::class.java

    override fun getFileName(): String = "MAIN-ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CacheHelperClass.resetLogFile(this)
        setTheme(R.style.Theme_StockSimulator)
    }

}