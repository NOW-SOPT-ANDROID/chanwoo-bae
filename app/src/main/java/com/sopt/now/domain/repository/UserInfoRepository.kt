package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.UserEntity

interface UserInfoRepository {
    fun saveUserInfo(user: UserEntity)
    fun saveCheckLogin(isChecked: Boolean)
    fun getUserInfo(): UserEntity
    fun getCheckLogin(): Boolean
    fun clear()
}
