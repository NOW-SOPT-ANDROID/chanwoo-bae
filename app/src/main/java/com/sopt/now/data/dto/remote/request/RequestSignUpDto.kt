package com.sopt.now.data.dto.remote.request

import com.sopt.now.domain.entity.UserEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignUpDto(
    @SerialName("authenticationId")
    val authenticationId: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("password")
    val password: String,
    @SerialName("phone")
    val phone: String
) {
    companion object {
        fun toSignUpDto(entity: UserEntity): RequestSignUpDto {
            return RequestSignUpDto(
                authenticationId = entity.id,
                nickname = entity.nickName,
                password = entity.password.toString(),
                phone = entity.phone
            )
        }
    }
}
