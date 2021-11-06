package com.sourabhverma.stocksimulator.main_activity

import androidx.lifecycle.MutableLiveData
import com.sourabhverma.stocksimulator.base.BaseViewModel
import org.json.JSONObject

class MainActivityViewModel : BaseViewModel(){
    private val repo : MainActivityRepo = MainActivityRepo()

    private var getIndices : MutableLiveData<JSONObject> = MutableLiveData()

    fun getIndices(symbol : String){
        repo.getIndices(symbol = symbol) {
            if (it != null){
                getIndices.postValue(it)
            } else {
                getIndices.postValue(null)
            }
        }
    }

    fun getIndicesOB() : MutableLiveData<JSONObject>{
        return getIndices
    }
}