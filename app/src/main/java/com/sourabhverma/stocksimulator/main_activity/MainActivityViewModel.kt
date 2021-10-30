package com.sourabhverma.stocksimulator.main_activity

import androidx.lifecycle.MutableLiveData
import com.sourabhverma.stocksimulator.base.BaseViewModel
import org.json.JSONArray
import org.json.JSONObject

class MainActivityViewModel : BaseViewModel(){
    private val repo : MainActivityRepo = MainActivityRepo()

    private var getIndices : MutableLiveData<JSONArray> = MutableLiveData()
    private var getGraphData : MutableLiveData<JSONObject> = MutableLiveData()

    fun getIndices() : MutableLiveData<JSONArray>{
        repo.getIndices {
            if (it != null){
                getIndices.postValue(it)
            } else {
                getIndices.postValue(null)
            }
        }
        return getIndices
    }
    fun getGraphDataOB() : MutableLiveData<JSONObject>{
        return getGraphData
    }
    fun getGraphData(name : String) {
        repo.getNiftyGraphData(name){
            if (it != null){
                getGraphData.postValue(it)
            } else {
                getGraphData.postValue(null)
            }
        }
    }

}