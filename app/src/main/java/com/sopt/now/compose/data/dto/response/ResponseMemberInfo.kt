package com.sopt.now.compose.data.dto.response

import com.sopt.now.compose.feature.model.User
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
    fun toMemberInfo() = User(
        id = authenticationId,
        password = "",
        nickName = nickname,
        phone = phone
    )
}
