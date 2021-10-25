package com.sourabhverma.stocksimulator.report.dialogs

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.databinding.FragmentReportDialogBinding
import com.sourabhverma.stocksimulator.deep.DeepActivity
import com.sourabhverma.stocksimulator.utils.CacheHelperClass
import com.sourabhverma.stocksimulator.utils.CommonUtils

class ReportDialogFragment : DialogFragment(){
    private lateinit var binding : FragmentReportDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(0F)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListener()

    }

    private fun clickListener() {
        binding.cancelButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.groupReport.setOnClickListener {
            dialog?.dismiss()
            writeLog("${CommonUtils().onClick} REPORT-A-BUG")
            val intentReport = Intent(context, DeepActivity::class.java)
            intentReport.putExtra(CommonUtils().fromStr, CommonUtils().reportDialogFragmentStr)
            intentReport.putExtra(CommonUtils().heading, getString(R.string.report_a_bug_heading))
            intentReport.putExtra(CommonUtils().type, getString(R.string.report))
            startActivity(intentReport)
        }

        binding.groupSuggestion.setOnClickListener {
            dialog?.dismiss()
            writeLog("${CommonUtils().onClick} SUGGEST-AN-IMPROVEMENT")
            val intentReport = Intent(context, DeepActivity::class.java)
            intentReport.putExtra(CommonUtils().fromStr, CommonUtils().reportDialogFragmentStr)
            intentReport.putExtra(CommonUtils().heading, getString(R.string.suggest_an_improvement_heading))
            intentReport.putExtra(CommonUtils().type, getString(R.string.suggestion))
            startActivity(intentReport)
        }

        binding.groupQuestion.setOnClickListener {
            dialog?.dismiss()
            writeLog("${CommonUtils().onClick} ASK-A-QUESTION")
            val intentReport = Intent(context, DeepActivity::class.java)
            intentReport.putExtra(CommonUtils().fromStr, CommonUtils().reportDialogFragmentStr)
            intentReport.putExtra(CommonUtils().heading, getString(R.string.ask_a_question_heading))
            intentReport.putExtra(CommonUtils().type, getString(R.string.query))
            startActivity(intentReport)
        }

    }

    private fun writeLog(value : String){
        val file = context?.let { CacheHelperClass.getLogFile(it) }
        file?.appendText("${CommonUtils().onClick},$value\n")
    }

}
