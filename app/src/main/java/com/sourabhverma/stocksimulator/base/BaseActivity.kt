package com.sourabhverma.stocksimulator.base

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.report.ReportDialogFragment
import com.sourabhverma.stocksimulator.utils.CacheHelperClass
import com.sourabhverma.stocksimulator.utils.CommonUtils

abstract class BaseActivity<T : ViewDataBinding, VM : ViewModel> : AppCompatActivity() {

    private lateinit var viewModel: VM
    private lateinit var binding: T

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    private var isShown : Boolean = false

    private val reportDialogFragment : ReportDialogFragment = ReportDialogFragment()

    private val lifecycleEventObserver = LifecycleEventObserver { _, event ->
        if (event.name == "ON_DESTROY"){
            isShown = false
        }
    }

    private val sensorEventListener : SensorEventListener = object : SensorEventListener{
        override fun onSensorChanged(p0: SensorEvent?) {
            val x = p0?.values?.get(0)
            if (x != null && x > 38F && !isShown) {
                if (checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    screenShot(window.decorView.rootView)?.let {
                        CacheHelperClass.putImage(applicationContext, "ScreenShot",
                            it
                        )
                    }
                } else {
                    requestPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                reportDialogFragment.show(supportFragmentManager, CommonUtils().reportDialogTag())
                reportDialogFragment.lifecycle.addObserver(lifecycleEventObserver)
                isShown = true
            }
        }
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }
    }

    fun requestPermission(permission: String){
        requestPermissions(arrayOf(permission), 0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                screenShot(window.decorView.rootView)?.let {
                    CacheHelperClass.putImage(applicationContext, "ScreenShot",
                        it
                    )
                }
            } else {
                Toast.makeText(applicationContext, getString(R.string.write_external_storage), Toast.LENGTH_SHORT).show()
            }
        }
    }

    open fun screenShot(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun checkPermission(permission: String):Boolean{
        val res : Int = ContextCompat.checkSelfPermission(applicationContext, permission)
        return res == PackageManager.PERMISSION_GRANTED
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
        registerSensor()
    }

    override fun onPause() {
        super.onPause()
        unregisterSensor()
    }

    fun unregisterSensor(){
        sensorManager.unregisterListener(sensorEventListener)
    }

    fun registerSensor(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    abstract fun getLayoutId() : Int

    abstract fun getViewModel() : Class<VM>

}