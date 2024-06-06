package com.sopt.now.domain.entity

data class ApiError(
    val errorMessage: String?
) : Exception(errorMessage)
