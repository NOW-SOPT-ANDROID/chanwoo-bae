package com.sopt.now.data_local.datasource

import com.sopt.now.data_local.dto.UserDto

interface SharedPreferenceDataSource {
    var memberId: Int
    fun saveUserInfo(userDto: UserDto?)
    fun getUserInfo(): UserDto

    fun clear()
}
