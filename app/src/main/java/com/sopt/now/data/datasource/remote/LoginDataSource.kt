package com.sopt.now.data.datasource.remote

import com.sopt.now.data.dto.remote.BaseResponse
import com.sopt.now.data.dto.remote.request.RequestLoginDto
import com.sopt.now.data.dto.remote.request.RequestSignUpDto
import com.sopt.now.data.dto.remote.response.ResponseMemberInfo
import retrofit2.Response

interface LoginDataSource {
    suspend fun signUp(request: RequestSignUpDto): BaseResponse<Unit>
    suspend fun login(request: RequestLoginDto): Response<BaseResponse<Unit>>
    suspend fun getMemberInfo(): BaseResponse<ResponseMemberInfo>
}
