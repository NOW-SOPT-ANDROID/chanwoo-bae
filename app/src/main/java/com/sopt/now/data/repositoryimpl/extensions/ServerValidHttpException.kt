package com.sopt.now.data.repositoryimpl.extensions

data class ServerValidHttpException(
    val errorMessage: String,
) : Exception(errorMessage)
