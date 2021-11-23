package com.sourabhverma.stocksimulator.main_activity

import android.view.View
import android.widget.ProgressBar
import com.sourabhverma.stocksimulator.base.BaseViewModel
import com.sourabhverma.stocksimulator.data.IndicesDAO

class MainActivityViewModel : BaseViewModel(){
    private val repo : MainActivityRepo = MainActivityRepo()

    fun getIndices(symbol : String, indicesDAO: IndicesDAO){
        repo.getIndices(symbol = symbol, indicesDAO = indicesDAO)
    }

    fun getBhavCopy(indicesDAO: IndicesDAO, progressHorizontal: ProgressBar, root: View){
        repo.getBhavCopy(indicesDAO, progressHorizontal, root)
    }

}