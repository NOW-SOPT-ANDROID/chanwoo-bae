package com.sopt.now.domain.entity

data class NetWorkConnectError(
    val errorMessage: String
) : Exception(errorMessage)
