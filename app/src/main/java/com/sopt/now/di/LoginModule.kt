package com.sopt.now.di

import com.sopt.now.data.repositoryimpl.LoginRepositoryImpl
import com.sopt.now.data_remote.api.LoginApiService
import com.sopt.now.data_remote.datasource.LoginDataSource
import com.sopt.now.data_remote.datasourceimpl.LoginDataSourceImpl
import com.sopt.now.di.qualifier.AUTH
import com.sopt.now.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginService(
        @AUTH retrofit: Retrofit
    ): LoginApiService = retrofit.create(LoginApiService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Singleton
        @Binds
        fun bindsLoginRepository(loginRepository: LoginRepositoryImpl):
                LoginRepository
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface DataSourceModule {
        @Singleton
        @Binds
        fun bindsLoginDataSource(loginDataSource: LoginDataSourceImpl): LoginDataSource
    }
}
