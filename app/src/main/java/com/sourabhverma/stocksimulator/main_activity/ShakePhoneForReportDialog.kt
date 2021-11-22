package com.sourabhverma.stocksimulator.main_activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.databinding.FragmentShakePhoneDialogBinding

class ShakePhoneForReportDialog : DialogFragment() {

    private lateinit var binding : FragmentShakePhoneDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(0F)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shake_phone_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            dialog?.dismiss()
        }
    }

}