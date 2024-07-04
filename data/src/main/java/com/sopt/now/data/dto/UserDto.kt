package com.sopt.now.data_local.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val password: String,
    val nickName: String,
    val phone: String
)
