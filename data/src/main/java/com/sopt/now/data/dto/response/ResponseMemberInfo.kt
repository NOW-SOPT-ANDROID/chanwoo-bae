package com.sopt.now.data.dto.response

import com.sopt.now.domain.entity.UserEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMemberInfo(
    @SerialName("authenticationId")
    val authenticationId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("phone")
    val phone: String
) {
    fun ResponseMemberInfo.toUserEntity(): UserEntity =
        UserEntity(
            id = authenticationId,
            password = "",
            nickName = nickname,
            phone = phone
        )
}
