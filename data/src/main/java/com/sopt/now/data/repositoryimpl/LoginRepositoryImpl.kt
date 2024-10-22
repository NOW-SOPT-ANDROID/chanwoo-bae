package com.sopt.now.data.repositoryimpl

import com.sopt.now.data.datasource.remote.LoginDataSource
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.mapper.toBaseResponseEntity
import com.sopt.now.data.mapper.toSignUpDto
import com.sopt.now.data.mapper.toUserEntity
import com.sopt.now.data.repositoryimpl.extensions.getResponseErrorMessage
import com.sopt.now.data.repositoryimpl.extensions.handleThrowable
import com.sopt.now.domain.entity.ApiError
import com.sopt.now.domain.entity.BaseResponseEntity
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {
    override suspend fun signUp(userEntity: UserEntity): Result<BaseResponseEntity> {
        return runCatching {
            loginDataSource.signUp(userEntity.toSignUpDto(userEntity)).toBaseResponseEntity()
        }.onFailure { throwable ->
            return throwable.handleThrowable()
        }
    }

    override suspend fun login(id: String, pwd: String): Result<Int?> {
        return runCatching {
            val response = loginDataSource.login(RequestLoginDto(id, pwd))
            if (response.isSuccessful) {
                response.headers()["location"]?.toInt()
            } else {
                throw ApiError(response.getResponseErrorMessage())
            }
        }
    }

    override suspend fun getMemberInfo(): Result<UserEntity> =
        runCatching {
            loginDataSource.getMemberInfo().data?.toUserEntity() ?: throw Exception("data is null")
        }
}
