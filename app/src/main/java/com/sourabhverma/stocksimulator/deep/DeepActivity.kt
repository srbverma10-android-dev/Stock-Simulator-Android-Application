package com.sourabhverma.stocksimulator.deep

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseActivity
import com.sourabhverma.stocksimulator.databinding.ActivityDeepBinding
import com.sourabhverma.stocksimulator.report.ReportFragment
import com.sourabhverma.stocksimulator.utils.CommonUtils

class DeepActivity : BaseActivity<ActivityDeepBinding, DeepActivityViewModel>() {
    override fun getLayoutId(): Int = R.layout.activity_deep

    override fun getViewModel(): Class<DeepActivityViewModel> = DeepActivityViewModel::class.java

    override fun getFileName(): String = "DEEP-ACTIVITY"

    private val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()

    private var shouldUnregister = false

    private var type : String = ""
    private var heading : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(null)
        super.onCreate(savedInstanceState)

        when(getDataFromIntent(intent)){
            CommonUtils().reportDialogFragmentStr -> openReportFragment()
            CommonUtils().defaultStr-> openReportFragment()
        }

    }

    private fun openReportFragment(){
        shouldUnregister=true

        val bundle = Bundle()
        bundle.putString(CommonUtils().type, type)
        bundle.putString(CommonUtils().heading, heading)

        val reportFragment = ReportFragment()
        reportFragment.arguments = bundle
        transaction.replace(R.id.frame_layout, reportFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getDataFromIntent(intent: Intent) : String{
        intent.getStringExtra(CommonUtils().type)?.let {
            type = it
        }
        intent.getStringExtra(CommonUtils().heading)?.let {
            heading = it
        }
        return try {
            intent.getStringExtra(CommonUtils().fromStr) as String
        } catch (e: Exception) {
            CommonUtils().defaultStr
        }
    }

    override fun onResume() {
        super.onResume()
        if (shouldUnregister)
            unregisterSensor()
        else
            registerSensor()
    }

    override fun onPause() {
        super.onPause()
        unregisterSensor()
    }


    override fun onBackPressed() {
        finish()
    }

}