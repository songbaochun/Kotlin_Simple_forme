package com.test.frame.base.bean

import com.google.gson.annotations.SerializedName

data class BaseBean<T>(
    //通过注解将返回的API进行重新命名
    @SerializedName("showapi_res_code")
    val code: Int,
    @SerializedName("showapi_res_error")
    val msg: String? = null,
    @SerializedName("showapi_res_body")
    val data: T
)
