package com.sopt.now.data.dto.remote

import com.sopt.now.domain.entity.BaseResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: T? = null
) {
    fun toBaseResponseEntity() = BaseResponseEntity(
        code,
        message
    )
}
