package com.sopt.now.data.datasource.local

import com.sopt.now.data.dto.local.UserDto

interface SharedPreferenceDataSource {
    var memberId: Int
    fun saveUserInfo(userDto: UserDto?)
    fun getUserInfo(): UserDto

    fun clear()
}
