package com.sourabhverma.stocksimulator.main_activity

import android.content.Context
import com.sourabhverma.stocksimulator.base.BaseViewModel
import com.sourabhverma.stocksimulator.data.IndicesDAO

class MainActivityViewModel : BaseViewModel(){
    private val repo : MainActivityRepo = MainActivityRepo()

    fun getIndices(symbol : String, indicesDAO: IndicesDAO){
        repo.getIndices(symbol = symbol, indicesDAO = indicesDAO)
    }
}