package com.sopt.now.compose.data.api

import com.sopt.now.compose.feature.util.KeyStorage.ID_DEFAULT

object UserIdProvider {
    private var _userId: Int = ID_DEFAULT
    val userId get() = _userId

    fun setUserId(newUserId: Int) {
        _userId = newUserId
    }
}
