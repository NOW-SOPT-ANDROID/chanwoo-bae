package com.sopt.now.di

import com.sopt.now.data.datasource.local.SharedPreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(MEMBER_ID, sharedPreferenceDataSource.memberId.toString())
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val MEMBER_ID = "memberId"
    }
}
