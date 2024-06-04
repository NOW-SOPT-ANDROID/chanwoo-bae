package com.sopt.now.compose.data.api

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.compose.BuildConfig
import com.sopt.now.compose.data.api.service.AuthService
import com.sopt.now.compose.data.api.service.ReqresService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiFactory {
    private const val AUTH_BASE_URL = BuildConfig.AUTH_BASE_URL
    private const val USER_BASE_URL = BuildConfig.REQRES_BASE_URL

    /* 1. 일단 로깅을 해줄 인터셉트를 만들어 줍니다.
       2. 이 함수는 Http통신 중 로깅을 담당
       3. loggingInterceptor.level을 HttpLoggingInterceptor.Level.BODY로 설정하여 요청 및 응답의 헤더 및 본문을 모두 로깅하도록 설정
    */
    private fun getLogOkHttpClient(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("Retrofit2", "CONNECTION INFO -> $message")
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    /* 1. OkHttpClient.Builder()를 통해 OkHttpClient를 만들어 준다.
     2. addInterceptor(getLogOkHttpClient())를 호출하여 앞서 정의한 로깅 Interceptor를 달아준다.
     3.build()로 okHttpClient 객체를 생성
    */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(getLogOkHttpClient())
        .addInterceptor(HeaderInterceptor()) // header interceptor 추가
        .build()

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient) // 여기에 달아준다.
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private inline fun <reified T> create(baseUrl: String): T {
        return createRetrofit(baseUrl).create(T::class.java)
    }

    object ServicePool {
        val authService: AuthService by lazy {
            create<AuthService>(AUTH_BASE_URL)
        }

        val userService: ReqresService by lazy {
            create<ReqresService>(USER_BASE_URL)
        }
    }
}
