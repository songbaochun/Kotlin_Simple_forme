package com.test.frame.base.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

//公共请求头参数
class CommonHeaderParameterInterceptor(var paras: Map<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val newBuilder = request.newBuilder()
        paras.entries.forEach {
            newBuilder.addHeader(it.key, it.value)
        }
        return chain.proceed(newBuilder.build())
    }

}