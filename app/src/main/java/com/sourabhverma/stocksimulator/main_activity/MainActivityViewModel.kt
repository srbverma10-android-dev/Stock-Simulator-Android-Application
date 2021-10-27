package com.sourabhverma.stocksimulator.main_activity

import androidx.lifecycle.MutableLiveData
import com.sourabhverma.stocksimulator.base.BaseViewModel
import org.json.JSONObject

class MainActivityViewModel : BaseViewModel(){
    private val repo : MainActivityRepo = MainActivityRepo()

    private var getNifty50 : MutableLiveData<JSONObject> = MutableLiveData()

    fun getNifty50() : MutableLiveData<JSONObject>{
        repo.getNifty50 {
            if (it != null){
                getNifty50.postValue(it)
            } else {
                getNifty50.postValue(null)
            }
        }
        return getNifty50
    }

}