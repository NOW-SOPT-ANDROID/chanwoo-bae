package com.sopt.now.compose.data.api.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberInfoResponse(
    @SerialName("authenticationId")
    val authenticationId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("phone")
    val phone: String
)
