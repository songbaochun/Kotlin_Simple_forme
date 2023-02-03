package com.test.frame.activity

import com.test.frame.base.activity.BaseActivity
import com.test.frame.wallpaper.R
import com.test.frame.wallpaper.databinding.ActivityMainBinding

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding>() {
    override fun initView() {
        setBarStatus(false)
    }

    override fun initData() {
        binding.viewTitleBar.text="Hell Word"

    }

}
