package com.sopt.now.data.repositoryimpl

import com.sopt.now.data.datasource.remote.LoginDataSource
import com.sopt.now.data.dto.remote.request.RequestLoginDto
import com.sopt.now.data.dto.remote.request.RequestSignUpDto
import com.sopt.now.data.repositoryimpl.extensions.getErrorMessage
import com.sopt.now.domain.entity.BaseResponseEntity
import com.sopt.now.data.repositoryimpl.extensions.ServerValidHttpException
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.LoginRepository
import retrofit2.HttpException
import javax.inject.Inject


class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository {
    override suspend fun signUp(userEntity: UserEntity): Result<BaseResponseEntity> {
        return runCatching {
            val requestDto = RequestSignUpDto.toSignUpDto(userEntity)
            val response = loginDataSource.signUp(requestDto)
            // 성공적인 응답 처리
            response.toBaseResponseEntity()
        }.onFailure { throwable ->
            // HttpException 처리
            return if (throwable is HttpException) {
                return Result.failure(ServerValidHttpException(throwable.getErrorMessage()))
            } else {
                Result.failure(throwable)
            }
        }
    }

    override suspend fun login(id: String, pwd: String): Result<Int?> {
        return runCatching {
            val response = loginDataSource.login(RequestLoginDto(id, pwd))
            if (response.isSuccessful) {
                response.headers()["location"]?.toInt()
            } else {
                throw ServerValidHttpException(response.getErrorMessage())
            }
        }
    }

    override suspend fun getMemberInfo(): Result<UserEntity> {
        return runCatching {
            loginDataSource.getMemberInfo().data?.toUserEntity() ?: error("Data is null")
        }
    }
}
