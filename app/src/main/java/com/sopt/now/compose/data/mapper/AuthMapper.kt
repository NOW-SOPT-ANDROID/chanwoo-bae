package com.sopt.now.compose.data.mapper

import com.sopt.now.compose.data.api.dto.request.SignUpRequest
import com.sopt.now.compose.data.api.dto.response.BaseResponse
import com.sopt.now.compose.data.api.dto.response.MemberInfoResponse
import com.sopt.now.compose.model.BaseApi
import com.sopt.now.compose.model.User

internal fun MemberInfoResponse.toUserData(): User =
    User(
        id = authenticationId,
        password = "",
        nickName = nickname,
        phone = phone
    )

internal fun User.toSignUpRemote(entity: User): SignUpRequest =
    SignUpRequest(
        authenticationId = entity.id,
        nickname = entity.nickName,
        password = entity.password,
        phone = entity.phone
    )

internal fun BaseResponse<Unit>.toBaseData() = BaseApi(
    code,
    message
)

