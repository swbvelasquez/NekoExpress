package com.swbvelasquez.nekoexpress.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class RetrofitInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Accept","application/json")
            .build()
        return chain.proceed(request)
    }
}