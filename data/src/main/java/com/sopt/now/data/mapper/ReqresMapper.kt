package com.sopt.now.data.mapper


import com.sopt.now.data_remote.dto.response.ResponseReqresDto
import com.sopt.now.domain.entity.ReqresEntity

internal fun ResponseReqresDto.toReqresList(): List<ReqresEntity> = data.map {
    ReqresEntity(
        id = it.id,
        email = it.email,
        firstName = it.firstName,
        lastName = it.lastName,
        avatar = it.avatar
    )
}
