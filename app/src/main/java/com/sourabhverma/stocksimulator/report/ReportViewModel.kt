package com.sourabhverma.stocksimulator.report

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.sourabhverma.stocksimulator.base.BaseViewModel
import com.sourabhverma.stocksimulator.data.GeneralResponseModel
import java.io.File

class ReportViewModel : BaseViewModel(){

    private val repo : ReportRepo = ReportRepo()

    private var addReportLiveData : MutableLiveData<GeneralResponseModel> = MutableLiveData()

    fun addReport(email: String, type: String, secondParam: String, screenShot: MutableList<Bitmap?>, logCsvFile: File, build: String, context : Context){
        repo.addReport(email, type, secondParam, screenShot, logCsvFile, build, context){
            if (it != null && it.code == 201){
                addReportLiveData.postValue(it)
            }
        }
    }

    fun getAddReportLiveData() : MutableLiveData<GeneralResponseModel>{
        return addReportLiveData
    }

}