package com.test.frame.base.http.interceptor

import com.test.frame.base.http.config.DOMAIN
import com.test.frame.base.http.parser.DefaultUrlParser
import com.test.frame.base.http.parser.UrlParser
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class ExtraBaseUrlInterceptor(var headerKey: String, var baseUrl: String) : Interceptor {
    private var mUrlParser: UrlParser = DefaultUrlParser()
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(processRequest(chain.request()))
    }

    private fun processRequest(request: Request): Request {
        val newBuilder = request.newBuilder()
        var httpUrl: HttpUrl? = null
        obtainDomainNameFromHeaders(request)?.let {
            if (it.isNotEmpty() && it.equals(headerKey)) {
                httpUrl = checkUrl(baseUrl)
                newBuilder.removeHeader(DOMAIN)
            }
        }
        httpUrl?.let {
            val newUrl = mUrlParser.parseUrl(it, request.url)
            return newBuilder.url(newUrl).build()
        }
        return newBuilder.build()
    }

    private fun obtainDomainNameFromHeaders(request: Request): String? {
        val headers = request.headers(DOMAIN)
        if (headers.size == 0)
            return ""
        if (headers.size > 1)
            throw IllegalArgumentException("Only one Domain-Name in the headers")
        return request.header(DOMAIN)
    }

    private fun checkUrl(url: String): HttpUrl? {
        return url.toHttpUrlOrNull()
    }
}