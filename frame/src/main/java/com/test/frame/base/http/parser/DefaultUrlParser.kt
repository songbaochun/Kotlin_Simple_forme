package com.test.frame.base.http.parser

import okhttp3.HttpUrl

class DefaultUrlParser : UrlParser {
    override fun parseUrl(domainUrl: HttpUrl, url: HttpUrl): HttpUrl {
        return url.newBuilder()
            .scheme(domainUrl.scheme)
            .host(domainUrl.host)
            .port(domainUrl.port)
            .build()
    }
}