package com.sopt.now.data.repositoryimpl

import com.sopt.now.data.datasource.remote.LoginDataSource
import com.sopt.now.data.dto.remote.request.RequestLoginDto
import com.sopt.now.data.dto.remote.request.RequestSignUpDto
import com.sopt.now.domain.entity.BaseResponseEntity
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginDataSource: LoginDataSource
) : LoginRepository {
    override suspend fun signUp(userEntity: UserEntity): Result<BaseResponseEntity> = runCatching {
        val requestDto = RequestSignUpDto.toSignUpDto(userEntity)
        loginDataSource.signUp(requestDto).toBaseResponseEntity()
    }

    override suspend fun login(id: String, pwd: String): Result<Int?> {
        return runCatching {
            loginDataSource.login(RequestLoginDto(id, pwd)).headers()["location"]?.toInt()
        }
    }

    override suspend fun getMemberInfo(): Result<UserEntity> {
        return runCatching {
            loginDataSource.getMemberInfo().data?.toUserEntity() ?: error("Data is null")
        }
    }
}
