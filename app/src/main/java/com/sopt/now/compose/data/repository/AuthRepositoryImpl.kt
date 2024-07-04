package com.sopt.now.compose.data.repository

import com.sopt.now.compose.data.api.dto.request.LoginRequest
import com.sopt.now.compose.data.api.service.AuthService
import com.sopt.now.compose.data.mapper.toBaseData
import com.sopt.now.compose.data.mapper.toSignUpRemote
import com.sopt.now.compose.data.mapper.toUserData
import com.sopt.now.compose.data.repository.util.getResponseErrorMessage
import com.sopt.now.compose.data.repository.util.handleThrowable
import com.sopt.now.compose.data_api.auth.AuthRepository
import com.sopt.now.compose.model.ApiError
import com.sopt.now.compose.model.BaseApi
import com.sopt.now.compose.model.User

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun signUp(user: User): Result<BaseApi> {
        return runCatching {
            authService.signUp(user.toSignUpRemote(user)).toBaseData()
        }.onFailure { throwable ->
            return throwable.handleThrowable()
        }
    }

    override suspend fun login(id: String, pwd: String): Result<Int?> {
        return runCatching {
            val response = authService.login(LoginRequest(id, pwd))
            if (response.isSuccessful) {
                response.headers()["location"]?.toInt()
            } else {
                throw ApiError(response.getResponseErrorMessage())
            }
        }
    }

    override suspend fun getMemberInfo(): Result<User> = runCatching {
        authService.getMemberInfo().data?.toUserData() ?: throw Exception("data is null")
    }
}