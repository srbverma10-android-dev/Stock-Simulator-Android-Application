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

    private val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()

    private var shouldUnregister = false

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
        val reportFragment = ReportFragment()
        transaction.replace(R.id.frame_layout, reportFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun getDataFromIntent(intent: Intent) : String{
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
    }

    override fun onBackPressed() {
        finish()
    }

}