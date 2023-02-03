package com.test.frame.activity

import com.test.frame.base.activity.BaseVmActivity
import com.test.frame.model.MainModel
import com.test.frame.wallpaper.R
import com.test.frame.wallpaper.databinding.ActivityMainBinding

class MainModelActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseVmActivity<ActivityMainBinding, MainModel>() {
    override fun initView() {

    }

    override fun initData() {
        viewModel.upDataTitle()
    }

    override fun initVmModel() = MainModel()
    override fun startObserve() {
        viewModel.testString.observe(this) {
            binding.viewTitleBar.text = it
        }
    }

}
