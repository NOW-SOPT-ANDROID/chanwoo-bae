package com.sopt.now.di

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sopt.now.BuildConfig
import com.sopt.now.data_local.datasource.SharedPreferenceDataSource
import com.sopt.now.di.qualifier.AUTH
import com.sopt.now.di.qualifier.REQRES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val AUTH_BASE_URL: String = BuildConfig.AUTH_BASE_URL
    private const val REQRES_BASE_URL: String = BuildConfig.REQRES_BASE_URL

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun providesJson(): Json =
        Json {
            isLenient = true
            prettyPrint = true
            explicitNulls = false
            ignoreUnknownKeys = true
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()

    @Provides
    @Singleton
    @AUTH
    fun provideHeaderInterceptor(sharedPreferenceDataSource: SharedPreferenceDataSource): HeaderInterceptor =
        HeaderInterceptor(sharedPreferenceDataSource)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.d("Retrofit2", "CONNECTION INFO -> $message")
        }.apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Singleton
    @Provides
    @AUTH
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(AUTH_BASE_URL, okHttpClient)

    @Singleton
    @Provides
    @REQRES
    fun provideReqresRetrofit(okHttpClient: OkHttpClient): Retrofit =
        createRetrofit(REQRES_BASE_URL, okHttpClient)


    private fun createRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
}
