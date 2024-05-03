package com.sopt.now.data.dto.remote.request

import com.sopt.now.domain.entity.UserEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDto(
    @SerialName("authenticationId")
    val authenticationId: String,
    @SerialName("password")
    val password: String
) {
    companion object {
        fun toLoginDto(entity: UserEntity): RequestLoginDto {
            return RequestLoginDto(
                authenticationId = entity.id,
                password = entity.password.toString()
            )
        }
    }
}
