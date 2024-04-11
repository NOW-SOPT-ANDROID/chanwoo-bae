package com.sopt.now.di

import com.sopt.now.data.datasource.local.SharedPreferenceDataSource
import com.sopt.now.data.datasourceimpl.local.SharedPreferenceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindsSharedPrefDataSource(sharedPrefDataSource: SharedPreferenceDataSourceImpl):
        SharedPreferenceDataSource
}
