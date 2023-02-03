package com.test.frame.base.http.interceptor

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


//公共 form表单参数
class CommonFormBodyParameterInterceptor(var params: HashMap<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newBuilder = chain.request().newBuilder()
        val jsonObject = JSONObject()
        params.entries.forEach {
            jsonObject.put(it.key, it.value)
        }
        val toRequestBody = jsonObject.toString()
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        newBuilder.post(toRequestBody)
        return chain.proceed(newBuilder.build())
    }


}

