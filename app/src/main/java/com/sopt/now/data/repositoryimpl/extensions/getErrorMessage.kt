package com.sopt.now.data.repositoryimpl.extensions

import com.sopt.now.data.repositoryimpl.extensions.KeyStorage.MESSAGE
import com.sopt.now.data.repositoryimpl.extensions.KeyStorage.UN_KNOWN_ERROR
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

// Throwable에서 에러 메시지 추출하는 확장 함수
fun Throwable.getErrorMessage(): String {
    return when (this) {
        is HttpException -> {
            val errorBody = response()?.errorBody()?.string() ?: return UN_KNOWN_ERROR
            val jsonObject = JSONObject(errorBody)
            jsonObject.getString(MESSAGE)
        }

        else -> "알 수 없는 오류가 발생했습니다."
    }
}

// Response에 대한 확장 함수
fun Response<*>?.getErrorMessage(): String {
    val errorBody = this?.errorBody()?.string() ?: return UN_KNOWN_ERROR
    return try {
        JSONObject(errorBody).getString(MESSAGE)
    } catch (e: JSONException) {
        "Error parsing error message"
    }
}

object KeyStorage {
    const val MESSAGE = "message"
    const val UN_KNOWN_ERROR = "Unknown error"
}
