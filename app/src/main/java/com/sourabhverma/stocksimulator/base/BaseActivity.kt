package com.sourabhverma.stocksimulator.base

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.report.ReportDialogFragment
import com.sourabhverma.stocksimulator.utils.CommonUtils

abstract class BaseActivity<T : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    private lateinit var viewModel: VM
    private lateinit var binding: T

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    private var isShown : Boolean = false

    private val reportDialogFragment : ReportDialogFragment = ReportDialogFragment()

    private val lifecycleEventObserver = LifecycleEventObserver { _, event ->
        if (event.name == "ON_DESTROY")
            isShown = false
    }

    private val sensorEventListener : SensorEventListener = object : SensorEventListener{
        override fun onSensorChanged(p0: SensorEvent?) {
            val x = p0?.values?.get(0)
            if (x != null && x > 38F && !isShown) {
                reportDialogFragment.show(supportFragmentManager, CommonUtils().reportDialogTag())
                reportDialogFragment.lifecycle.addObserver(lifecycleEventObserver)
                isShown = true
            }
        }
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }
    }

    override fun onBackPressed() {
        isShown = false
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_StockSimulator)

        binding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = ViewModelProvider(this).get(getViewModel())

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }

    abstract fun getLayoutId() : Int

    abstract fun getViewModel() : Class<VM>

}