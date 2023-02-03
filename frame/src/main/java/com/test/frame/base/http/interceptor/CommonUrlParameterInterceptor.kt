package com.test.frame.base.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

//url链接的公共参数
class CommonUrlParameterInterceptor(var paras: Map<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val httpUrl = request.url.newBuilder()
        paras.entries.forEach {
            httpUrl.addQueryParameter(it.key, it.value)
        }
        request = request.newBuilder().url(httpUrl.build()).build()
        return chain.proceed(request)
    }
}