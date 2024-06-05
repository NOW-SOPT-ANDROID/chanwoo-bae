package com.sopt.now.compose.data.api.service

import com.sopt.now.compose.data.api.service.ApiKeyStorage.INFO
import com.sopt.now.compose.data.api.service.ApiKeyStorage.JOIN
import com.sopt.now.compose.data.api.service.ApiKeyStorage.LOGIN
import com.sopt.now.compose.data.api.service.ApiKeyStorage.MEMBER
import com.sopt.now.compose.data.api.dto.request.LoginRequest
import com.sopt.now.compose.data.api.dto.request.SignUpRequest
import com.sopt.now.compose.data.api.dto.response.BaseResponse
import com.sopt.now.compose.data.api.dto.response.MemberInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("$MEMBER/$JOIN")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): BaseResponse<Unit>

    @POST("$MEMBER/$LOGIN")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<BaseResponse<Unit>>

    @GET("$MEMBER/$INFO")
    suspend fun getMemberInfo(): BaseResponse<MemberInfoResponse>
}
