package com.sourabhverma.stocksimulator.report

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseFragment
import com.sourabhverma.stocksimulator.databinding.FragmentReportBinding
import com.sourabhverma.stocksimulator.utils.CacheHelperClass
import com.sourabhverma.stocksimulator.utils.CommonUtils


class ReportFragment : BaseFragment<FragmentReportBinding, ReportViewModel>(), ClickListener {
    override fun getLayoutId(): Int = R.layout.fragment_report

    override fun getViewModel(): Class<ReportViewModel> = ReportViewModel::class.java

    override fun getFileName(): String = "REPORT-FRAGMENT"

    private var bitmapArray : MutableList<Bitmap?> = mutableListOf()
    private val screenShotAdapter = ScreenShotAdapter()

    private val build = "{\"buildName\":\"Google pixal 4a\"}"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextAccToOptionSelectedByUser()

        listeners()

        bitmapArray.add(context?.let { CacheHelperClass.getImage(it, "ScreenShot") })
        context?.let {
            screenShotAdapter.setListOfBitmap(bitmapArray, it, this)
            if (bitmapArray.isNotEmpty() && bitmapArray[0]!=null){
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
                arguments?.getString(CommonUtils().type)?.let { it1 ->
                    context?.let { it2 -> CacheHelperClass.getLogFile(it2) }?.let { it3 ->
                        viewModel.addReport(binding.emailEditText.text.toString(),
                            it1, binding.feedbackEditText.text.toString(), bitmapArray,
                            it3, build, requireContext()
                        )
                    }
                }

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

        viewModel.getAddReportLiveData().observe(viewLifecycleOwner, {
            if (it?.code != null && it.code == 201){
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun setTextAccToOptionSelectedByUser() {
        writeLog(CommonUtils().passedData, "${CommonUtils().passedData} to REPORT-FRAGMENT DATA:- ${arguments?.getString(CommonUtils().type)}")
        binding.feedbackEditTextLayout.hint = getString(R.string.feedback_hint, arguments?.getString(CommonUtils().type))
        binding.feedbackText.text = arguments?.getString(CommonUtils().heading)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 11 && data != null){
            val selectedImage : Uri? = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, selectedImage)
            bitmapArray.add(bitmap)
            screenShotAdapter.notifyItemInserted(bitmapArray.size-1)
        }
    }

    override fun addMoreScreenShot() {
        val intent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 11)
    }

    override fun removeThis(position: Int) {
        if (position < bitmapArray.size) {
            bitmapArray.removeAt(position)
            screenShotAdapter.notifyItemRemoved(position)
        }
    }

}