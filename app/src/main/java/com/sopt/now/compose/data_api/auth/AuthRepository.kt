package com.sopt.now.compose.data_api.auth

import com.sopt.now.compose.model.BaseApi
import com.sopt.now.compose.model.User

interface AuthRepository {
    suspend fun signUp(user: User): Result<BaseApi>
    suspend fun login(id: String, pwd: String): Result<Int?>
    suspend fun getMemberInfo(): Result<User>
}
