package com.sourabhverma.stocksimulator.main_activity

import androidx.lifecycle.MutableLiveData
import com.sourabhverma.stocksimulator.base.BaseViewModel
import org.json.JSONObject

class MainActivityViewModel : BaseViewModel(){
    private val repo : MainActivityRepo = MainActivityRepo()

    private var getIndices : MutableLiveData<JSONObject> = MutableLiveData()

    fun getIndices() : MutableLiveData<JSONObject>{
        repo.getIndices {
            if (it != null){
                getIndices.postValue(it)
            } else {
                getIndices.postValue(null)
            }
        }
        return getIndices
    }

}