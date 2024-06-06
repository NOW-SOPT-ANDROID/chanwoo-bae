package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.BaseResponseEntity
import com.sopt.now.domain.entity.UserEntity

interface LoginRepository {
    suspend fun signUp(userEntity: UserEntity): Result<BaseResponseEntity>
    suspend fun login(id: String, pwd: String): Result<Int?>
    suspend fun getMemberInfo(): Result<UserEntity>
}
