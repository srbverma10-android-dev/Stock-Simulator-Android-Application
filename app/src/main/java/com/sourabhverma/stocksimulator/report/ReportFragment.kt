package com.sourabhverma.stocksimulator.report

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseFragment
import com.sourabhverma.stocksimulator.base.BaseViewModel
import com.sourabhverma.stocksimulator.databinding.FragmentReportBinding
import com.sourabhverma.stocksimulator.utils.CacheHelperClass
import com.sourabhverma.stocksimulator.utils.CommonUtils

class ReportFragment : BaseFragment<FragmentReportBinding, BaseViewModel>() {
    override fun getLayoutId(): Int = R.layout.fragment_report

    override fun getViewModel(): Class<BaseViewModel> = BaseViewModel::class.java

    override fun getFileName(): String = "REPORT-FRAGMENT"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextAccToOptionSelectedByUser()

        listeners()

        val screenShotAdapter = ScreenShotAdapter()
        arrayOf(context?.let { CacheHelperClass.getImage(it, "ScreenShot") }).let {
            screenShotAdapter.setListOfBitmap(
                it
            )
            if (it[0]!=null){
                binding.listOfScreenShot.visibility = View.VISIBLE
                binding.screenShotsText.visibility = View.VISIBLE
            } else {
                binding.listOfScreenShot.visibility = View.GONE
                binding.screenShotsText.visibility = View.GONE
            }
        }
        binding.listOfScreenShot.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = screenShotAdapter
        }

    }

    private fun listeners() {
        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty() && text.isNotBlank()) {
                if (!CommonUtils().isValidEmail(text)){
                    binding.emailEditTextLayout.isErrorEnabled = true
                    binding.emailEditTextLayout.error = getString(R.string.enter_email_error)
                } else {
                    binding.emailEditTextLayout.isErrorEnabled = false
                    binding.emailEditTextLayout.error = null
                }
            } else {
                binding.emailEditTextLayout.isErrorEnabled = false
                binding.emailEditTextLayout.error = null
            }
        }

        binding.feedbackEditText.doOnTextChanged { text, _, _, _ ->
            if (text != null && text.isEmpty() && text.isBlank()) {
                binding.feedbackEditTextLayout.isErrorEnabled = true
                binding.feedbackEditTextLayout.error = getString(R.string.this_can_not_be_null)
            } else {
                binding.feedbackEditTextLayout.isErrorEnabled = false
                binding.feedbackEditTextLayout.error = null
            }
        }

        binding.submitButton.setOnClickListener {
            if (!binding.emailEditTextLayout.isErrorEnabled && !binding.feedbackEditTextLayout.isErrorEnabled &&
                    CommonUtils().isValidEmail(binding.emailEditText.text.toString()) && binding.feedbackEditText.text != null &&
                    binding.feedbackEditText.text.toString().isNotBlank() && binding.feedbackEditText.text.toString().isNotEmpty()){
                //upload data to the server



            } else {
                writeLog(CommonUtils().showError, "IN:- REPORT-FRAGMENT")
                if (!CommonUtils().isValidEmail(binding.emailEditText.text.toString())){
                    binding.emailEditTextLayout.isErrorEnabled = true
                    binding.emailEditTextLayout.error = getString(R.string.enter_email_error)
                } else {
                    binding.emailEditTextLayout.isErrorEnabled = false
                    binding.emailEditTextLayout.error = null
                }
                if (binding.feedbackEditText.text.toString().isEmpty() && binding.feedbackEditText.text.toString().isBlank()){
                    binding.feedbackEditTextLayout.isErrorEnabled = true
                    binding.feedbackEditTextLayout.error = getString(R.string.this_can_not_be_null)
                } else {
                    binding.feedbackEditTextLayout.isErrorEnabled = false
                    binding.feedbackEditTextLayout.error = null
                }
            }
        }

        binding.cancelButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setTextAccToOptionSelectedByUser() {
        writeLog(CommonUtils().passedData, "${CommonUtils().passedData} to REPORT-FRAGMENT DATA:- ${arguments?.getString(CommonUtils().type)}")
        binding.feedbackEditTextLayout.hint = getString(R.string.feedback_hint, arguments?.getString(CommonUtils().type))
        binding.feedbackText.text = arguments?.getString(CommonUtils().heading)
    }

}