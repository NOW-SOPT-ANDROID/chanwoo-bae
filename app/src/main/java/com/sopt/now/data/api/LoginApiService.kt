package com.sopt.now.data.api

import com.sopt.now.data.api.ApiKeyStorage.INFO
import com.sopt.now.data.api.ApiKeyStorage.JOIN
import com.sopt.now.data.api.ApiKeyStorage.LOGIN
import com.sopt.now.data.api.ApiKeyStorage.MEMBER
import com.sopt.now.data.dto.remote.BaseResponse
import com.sopt.now.data.dto.remote.request.RequestLoginDto
import com.sopt.now.data.dto.remote.request.RequestSignUpDto
import com.sopt.now.data.dto.remote.response.ResponseMemberInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {
    @POST("$MEMBER/$JOIN")
    suspend fun signUp(
        @Body request: RequestSignUpDto
    ): BaseResponse<Unit>

    @POST("$MEMBER/$LOGIN")
    suspend fun login(
        @Body request: RequestLoginDto
    ): Response<BaseResponse<Unit>>

    @GET("$MEMBER/$INFO")
    suspend fun getMemberInfo(): BaseResponse<ResponseMemberInfo>
}
