package com.sourabhverma.stocksimulator.main_activity

import android.os.Bundle
import android.text.Spannable
import android.text.style.ImageSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.snackbar.Snackbar
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseActivity
import com.sourabhverma.stocksimulator.databinding.ActivityMainBinding
import com.sourabhverma.stocksimulator.ui.PageIndicator
import com.sourabhverma.stocksimulator.utils.CacheHelperClass
import com.sourabhverma.stocksimulator.utils.CommonUtils
import com.sourabhverma.stocksimulator.utils.SharedPrefManager
import org.json.JSONArray

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): Class<MainActivityViewModel> = MainActivityViewModel::class.java

    override fun getFileName(): String = "MAIN-ACTIVITY"

    private var niftyAdapter: NiftyAdapter = NiftyAdapter()
    private lateinit var lm : LinearLayoutManager
    private var jsonArray = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CacheHelperClass.resetLogFile(this)
        setTheme(R.style.Theme_StockSimulator)
        setData()
        getData()
        lm =  LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper: SnapHelper = PagerSnapHelper()
        val itemDecoration = PageIndicator()
        binding.recyclerViewNifty.apply {
            layoutManager = lm
            adapter = niftyAdapter
            addItemDecoration(itemDecoration)
        }
        snapHelper.attachToRecyclerView(binding.recyclerViewNifty)
        viewModel.getGraphDataOB().observe(this@MainActivity, {it2->
            if (it2 != null) {
                for (i in 0 until jsonArray.length()){
                    if (jsonArray.getJSONObject(i).getString("indexSymbol") == it2.getString("name")){
                        val jsonObject = jsonArray.getJSONObject(i)
                        jsonObject.put("graphData", it2.getJSONArray("grapthData"))
                        niftyAdapter.addJsonObject(jsonObject)
                        niftyAdapter.notifyItemInserted(niftyAdapter.itemCount)
                    }
                }
            }
        })
    }

    private fun getData(){
        viewModel.getIndices().observe(this, {it1->
            if (it1 != null){
                for (i in 0 until it1.length()){
                    viewModel.getGraphData(it1.getJSONObject(i).getString("indexSymbol"))
                    jsonArray.put(it1.getJSONObject(i))
                }

            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setData() {
        binding.userName.text = SharedPrefManager().getUsername(this)
        if (SharedPrefManager().getShouldShowFirstSuccessCard(this)) {
            binding.success.root.visibility = View.VISIBLE
            binding.success.congratsText.text = buildSpannedString {
                append(getString(R.string.congrats_text1, CommonUtils().changeToAmtInt(this@MainActivity)))
                bold {
                    append(" ")
                    append(getString(R.string.congrats_text2))
                }
            }
            binding.success.checkMostActive.text = buildSpannedString {
                append(getString(R.string.check_most_active_stock))
                val d = AppCompatResources.getDrawable(this@MainActivity, R.drawable.ic_right_arrow)
                val imageSpan = d?.let {
                    d.setBounds(0, 0, d.intrinsicWidth, d.intrinsicHeight)
                    ImageSpan(
                        it,
                        ImageSpan.ALIGN_BOTTOM)
                }
                append("  ")
                this.setSpan(imageSpan, this.length-1, this.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
            binding.success.cancelButton.setOnClickListener {
                if (SharedPrefManager().setShouldShowFirstSuccessCard(this@MainActivity, false)) {
                    binding.success.root.visibility = View.GONE
                } else {
                    Snackbar.make(binding.root, getString(R.string.something_went_wrong), Snackbar.LENGTH_SHORT).show()
                }
            }
            SharedPrefManager().setCurrentAmount(this, CommonUtils().amount)
        } else {
            binding.success.root.visibility = View.GONE
        }
    }

}