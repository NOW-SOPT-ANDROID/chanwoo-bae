package com.sopt.now.compose.data.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(MEMBER_ID, UserIdProvider.userId.toString())
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val MEMBER_ID = "memberId"
    }
}
