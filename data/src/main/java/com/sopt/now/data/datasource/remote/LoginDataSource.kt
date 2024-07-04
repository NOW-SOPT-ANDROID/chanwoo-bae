package com.sopt.now.data.datasource.remote


import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data.dto.response.BaseResponse
import com.sopt.now.data.dto.response.ResponseMemberInfo
import com.sopt.now.data_remote.dto.request.RequestSignUpDto
import retrofit2.Response

interface LoginDataSource {
    suspend fun signUp(request: RequestSignUpDto): BaseResponse<Unit>
    suspend fun login(request: RequestLoginDto): Response<BaseResponse<Unit>>
    suspend fun getMemberInfo(): BaseResponse<ResponseMemberInfo>
}
