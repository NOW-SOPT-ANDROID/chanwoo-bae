package com.sopt.now.domain.entity

data class UserEntity(
    val id: String,
    val password: String? = null,
    val nickName: String,
    val phone: String
)
