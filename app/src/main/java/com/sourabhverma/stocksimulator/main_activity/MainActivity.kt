package com.sourabhverma.stocksimulator.main_activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Spannable
import android.text.style.ImageSpan
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.sourabhverma.stocksimulator.R
import com.sourabhverma.stocksimulator.base.BaseActivity
import com.sourabhverma.stocksimulator.data.AppDatabase
import com.sourabhverma.stocksimulator.databinding.ActivityMainBinding
import com.sourabhverma.stocksimulator.ui.PageIndicator
import com.sourabhverma.stocksimulator.utils.CacheHelperClass
import com.sourabhverma.stocksimulator.utils.CommonUtils
import com.sourabhverma.stocksimulator.utils.SharedPrefManager

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): Class<MainActivityViewModel> = MainActivityViewModel::class.java

    override fun getFileName(): String = "MAIN-ACTIVITY"

    private var niftyAdapter: NiftyAdapter = NiftyAdapter()
    private lateinit var lm : LinearLayoutManager

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
    }

    private fun getData(){
        val db = Room.databaseBuilder(
            this@MainActivity,
            AppDatabase::class.java, "INDICES"
        ).build()
        val indicesDAO = db.indicesDao()

        for (str in CommonUtils().getIndicesSymbol()){
            viewModel.getIndices(str, indicesDAO)
        }
        indicesDAO.getAll().observe(this@MainActivity, {
            niftyAdapter.setData(it.sortedBy { it2->
                it2.uid
            })
            niftyAdapter.notifyDataSetChanged()
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData() {
        niftyAdapter.setContext(this)
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