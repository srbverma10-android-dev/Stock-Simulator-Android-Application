package com.sourabhverma.stocksimulator.report

import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseFragment
import com.sourabhverma.stocksimulator.databinding.FragmentReportBinding
import com.sourabhverma.stocksimulator.deep.DeepActivityViewModel

class ReportFragment : BaseFragment<FragmentReportBinding, DeepActivityViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_report

    override fun getViewModel(): Class<DeepActivityViewModel> = DeepActivityViewModel::class.java

}