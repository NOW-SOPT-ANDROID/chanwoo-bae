package com.sopt.now.compose.data.request

import com.sopt.now.compose.feature.model.User
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
        fun toSignUpDto(entity: User): RequestSignUpDto {
            return RequestSignUpDto(
                authenticationId = entity.id,
                nickname = entity.nickName,
                password = entity.password.toString(),
                phone = entity.phone
            )
        }
    }
}
