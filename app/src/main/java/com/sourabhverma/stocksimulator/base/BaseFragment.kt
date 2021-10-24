package com.sourabhverma.stocksimulator.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourabhverma.stocksimulator.utils.CacheHelperClass
import com.sourabhverma.stocksimulator.utils.CommonUtils

abstract class BaseFragment<T : ViewDataBinding, VM : ViewModel> : Fragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        viewModel = ViewModelProvider(this).get(getViewModel())

        writeLog(CommonUtils().lifeCycle, "onCreate-${getFileName()}")

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        writeLog(CommonUtils().lifeCycle, "onResume-${getFileName()}")
    }

    override fun onPause() {
        super.onPause()
        writeLog(CommonUtils().lifeCycle, "onPause-${getFileName()}")
    }

    override fun onStop() {
        super.onStop()
        writeLog(CommonUtils().lifeCycle, "onStop-${getFileName()}")
    }

    override fun onStart() {
        super.onStart()
        writeLog(CommonUtils().lifeCycle, "onStart-${getFileName()}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        writeLog(CommonUtils().lifeCycle, "onViewCreated-${getFileName()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        writeLog(CommonUtils().lifeCycle, "onDestroy-${getFileName()}")
    }

    fun writeLog(tag : String, value : String){
        val file = context?.let { CacheHelperClass.getLogFile(it) }
        file?.appendText("$tag,$value\n")
    }

    abstract fun getLayoutId() : Int

    abstract fun getViewModel() : Class<VM>

    abstract fun getFileName() : String

}