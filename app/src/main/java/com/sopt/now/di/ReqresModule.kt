package com.sopt.now.di

import com.sopt.now.data.api.ReqresService
import com.sopt.now.data.datasource.remote.ReqresDataSource
import com.sopt.now.data.datasourceimpl.remote.ReqresDataSourceImpl
import com.sopt.now.data.repositoryimpl.ReqresRepositoryImpl
import com.sopt.now.di.qualifier.REQRES
import com.sopt.now.domain.repository.ReqresRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ReqresModule {

    @Provides
    @Singleton
    fun provideReqresService(
        @REQRES retrofit: Retrofit
    ): ReqresService = retrofit.create(ReqresService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Singleton
        @Binds
        fun bindsReqresRepository(reqresDomainRepository: ReqresRepositoryImpl):
            ReqresRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Singleton
        @Binds
        fun bindsreqresDataSource(reqresDataSource: ReqresDataSourceImpl): ReqresDataSource
    }
}