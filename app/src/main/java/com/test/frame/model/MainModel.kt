package com.test.frame.model

import com.test.frame.base.BaseEntityEvent
import com.test.frame.base.BaseViewModel

class MainModel : BaseViewModel() {
    val testString = BaseEntityEvent<String>()

    //获取任务列表
    fun upDataTitle() {
        testString.postValue("哈哈哈哈！")
    }
}