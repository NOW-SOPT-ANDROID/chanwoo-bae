package com.sopt.now.data.datasourceimpl.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sopt.now.data.datasource.local.SharedPreferenceDataSource
import com.sopt.now.data.dto.local.UserDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SharedPreferenceDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferenceDataSource {
    override var checkLogin: Int
        get() = sharedPreferences.getInt(CHECK_LOGIN, -1)
        set(value) = sharedPreferences.edit { putInt(CHECK_LOGIN, value) }

    override fun saveUserInfo(userDto: UserDto?) {
        val json = Json.encodeToString(userDto)
        sharedPreferences.edit { putString(USER_INFO, json) }
    }

    override fun getUserInfo(): UserDto {
        val json = sharedPreferences.getString(USER_INFO, "")
        if (json.isNullOrBlank()) return UserDto("", "", "", "")
        return Json.decodeFromString(json)
    }

    override fun clear() {
        sharedPreferences.edit {
            remove(USER_INFO)
            remove(CHECK_LOGIN)
        }
    }

    companion object {
        const val USER_INFO = "userId"
        const val CHECK_LOGIN = "checkLogin"
    }
}
