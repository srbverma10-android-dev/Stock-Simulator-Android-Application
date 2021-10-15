package com.sourabhverma.stocksimulator.report

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

}
