package com.sourabhverma.stocksimulator.report

import android.os.Bundle
import android.view.View
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseFragment
import com.sourabhverma.stocksimulator.databinding.FragmentReportBinding
import com.sourabhverma.stocksimulator.deep.DeepActivityViewModel
import com.sourabhverma.stocksimulator.utils.CommonUtils

class ReportFragment : BaseFragment<FragmentReportBinding, DeepActivityViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_report

    override fun getViewModel(): Class<DeepActivityViewModel> = DeepActivityViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.feedbackEditTextLayout.hint = getString(R.string.feedback_hint, arguments?.getString(CommonUtils().type))
        binding.feedbackText.text = arguments?.getString(CommonUtils().heading)
    }

}