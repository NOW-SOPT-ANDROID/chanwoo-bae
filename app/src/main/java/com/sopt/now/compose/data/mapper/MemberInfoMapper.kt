package com.sopt.now.compose.data.mapper

import com.sopt.now.compose.data.api.dto.response.MemberInfoResponse
import com.sopt.now.compose.model.User

internal fun MemberInfoResponse.toUserData(): User =
    User(
        id = authenticationId,
        password = "",
        nickName = nickname,
        phone = phone
    )
