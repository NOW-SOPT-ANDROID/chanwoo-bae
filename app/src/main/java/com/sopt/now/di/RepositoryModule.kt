package com.sopt.now.di

import com.sopt.now.data.repositoryimpl.UserInfoRepositoryImpl
import com.sopt.now.domain.repository.UserInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindsUserInfoRepository(
        userInfoRepository: UserInfoRepositoryImpl
    ): UserInfoRepository
}
