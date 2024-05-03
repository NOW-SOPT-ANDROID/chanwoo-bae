package com.sopt.now.domain.entity

data class BaseResponseEntity(
    val code: Int,
    val message: String,
    val header: Int? = null
)
