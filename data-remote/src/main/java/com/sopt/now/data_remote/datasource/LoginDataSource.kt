package com.sopt.now.data_remote.datasource


import com.sopt.now.data_remote.dto.request.RequestLoginDto
import com.sopt.now.data_remote.dto.request.RequestSignUpDto
import com.sopt.now.data_remote.dto.response.ResponseMemberInfo
import com.sopt.now.data_remote.dto.response.BaseResponse
import retrofit2.Response

interface LoginDataSource {
    suspend fun signUp(request: RequestSignUpDto): BaseResponse<Unit>
    suspend fun login(request: RequestLoginDto): Response<BaseResponse<Unit>>
    suspend fun getMemberInfo(): BaseResponse<ResponseMemberInfo>
}
