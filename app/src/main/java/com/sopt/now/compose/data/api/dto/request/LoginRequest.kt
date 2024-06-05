package com.sopt.now.compose.data.api.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("authenticationId")
    val authenticationId: String,
    @SerialName("password")
    val password: String
)
