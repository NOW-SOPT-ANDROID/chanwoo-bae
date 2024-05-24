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
    override var memberId: Int
        get() = sharedPreferences.getInt(MEMBER_ID, -1)
        set(value) = sharedPreferences.edit { putInt(MEMBER_ID, value) }

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
            remove(MEMBER_ID)
        }
    }

    companion object {
        const val USER_INFO = "userId"
        const val MEMBER_ID = "memberId"
    }
}
