package com.sopt.now.compose.data.repository.util

import com.sopt.now.compose.data.api.dto.response.BaseResponse
import com.sopt.now.compose.model.ApiError
import com.sopt.now.compose.model.NetWorkConnectError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun Throwable.getErrorMessage(): String {
    return when (this) {
        is HttpException -> {
            val errorBody = response()?.errorBody()?.string() ?: return "Unknown error"
            val errorResponse = Json.decodeFromString<BaseResponse<Unit>>(errorBody)
            errorResponse.message
        }

        else -> "An unknown error occurred."
    }
}

fun <T> Throwable.handleThrowable(): Result<T> {
    return Result.failure(
        when (this) {
            is HttpException -> ApiError(this.getErrorMessage())
            is IOException -> NetWorkConnectError("인터넷에 연결 해 주세요")
            else -> this
        }
    )
}

fun Response<*>?.getResponseErrorMessage(): String {
    val errorBody = this?.errorBody()?.string() ?: return "Unknown error"
    val errorResponse = Json.decodeFromString<BaseResponse<Unit>>(errorBody)
    return errorResponse.message
}
