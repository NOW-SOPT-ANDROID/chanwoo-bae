package com.sopt.now.data.mapper


import com.sopt.now.data_local.dto.UserDto
import com.sopt.now.data_remote.dto.request.RequestSignUpDto
import com.sopt.now.data_remote.dto.response.BaseResponse
import com.sopt.now.data_remote.dto.response.ResponseMemberInfo
import com.sopt.now.domain.entity.BaseResponseEntity
import com.sopt.now.domain.entity.UserEntity

internal fun ResponseMemberInfo.toUserEntity(): UserEntity =
    UserEntity(
        id = authenticationId,
        password = "",
        nickName = nickname,
        phone = phone
    )

internal fun UserEntity.toSignUpDto(entity: UserEntity): RequestSignUpDto =
    RequestSignUpDto(
        authenticationId = entity.id,
        nickname = entity.nickName,
        password = entity.password.orEmpty(),
        phone = entity.phone
    )

internal fun BaseResponse<Unit>.toBaseResponseEntity() = BaseResponseEntity(
    code,
    message
)

internal fun UserDto.toUserEntity(): UserEntity =
    UserEntity(
        id, password, nickName, phone
    )
