package com.sopt.now.compose.model

data class NetWorkConnectError(
    val errorMessage: String
) : Exception(errorMessage)
