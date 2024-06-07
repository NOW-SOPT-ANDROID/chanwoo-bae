package com.sopt.now.di

import com.sopt.now.data.repositoryimpl.UserInfoRepositoryImpl
import com.sopt.now.data_local.datasource.SharedPreferenceDataSource
import com.sopt.now.data_local.datasourceimpl.SharedPreferenceDataSourceImpl
import com.sopt.now.domain.repository.UserInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Singleton
        @Binds
        fun bindsUserInfoRepository(
            userInfoRepository: UserInfoRepositoryImpl
        ): UserInfoRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Singleton
        @Binds
        fun bindsSharedPrefDataSource(sharedPrefDataSource: SharedPreferenceDataSourceImpl):
                SharedPreferenceDataSource
    }
}
