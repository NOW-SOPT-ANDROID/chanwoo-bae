package com.sopt.now.compose.data.mapper

import com.sopt.now.compose.data.api.dto.response.ReqresResponse
import com.sopt.now.compose.model.ReqresEntity

internal fun ReqresResponse.toReqresData(): List<ReqresEntity> = data.map { it ->
    ReqresEntity(
        id = it.id,
        email = it.email,
        firstName = it.firstName,
        lastName = it.lastName,
        avatar = it.avatar
    )
}

