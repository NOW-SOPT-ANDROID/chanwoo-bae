package com.sopt.now.data.datasourceimpl.remote

import com.sopt.now.data.api.LoginApiService
import com.sopt.now.data.datasource.remote.LoginDataSource
import com.sopt.now.data.dto.remote.BaseResponse
import com.sopt.now.data.dto.remote.request.RequestLoginDto
import com.sopt.now.data.dto.remote.request.RequestSignUpDto
import com.sopt.now.data.dto.remote.response.ResponseMemberInfo
import javax.inject.Inject
import retrofit2.Response

class LoginDataSourceImpl @Inject constructor(
    private val loginApiService: LoginApiService
) : LoginDataSource {

    override suspend fun signUp(request: RequestSignUpDto): BaseResponse<Unit> =
        loginApiService.signUp(request)

    override suspend fun login(request: RequestLoginDto): Response<BaseResponse<Unit>> =
        loginApiService.login(request)

    override suspend fun getMemberInfo(memberId: Int): BaseResponse<ResponseMemberInfo> =
        loginApiService.getMemberInfo(memberId)
}
