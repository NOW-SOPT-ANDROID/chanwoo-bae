package com.sopt.now.compose.model

data class ApiError(
    val errorMessage: String?
) : Exception(errorMessage)
