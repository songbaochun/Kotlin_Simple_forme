package com.test.frame.base.http.parser

import okhttp3.HttpUrl

interface UrlParser {
    fun parseUrl(domainUrl: HttpUrl, url: HttpUrl): HttpUrl
}