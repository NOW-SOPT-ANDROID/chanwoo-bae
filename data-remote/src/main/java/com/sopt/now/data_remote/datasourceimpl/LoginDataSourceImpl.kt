package com.sopt.now.data_remote.datasourceimpl


import com.sopt.now.data.datasource.remote.LoginDataSource
import com.sopt.now.data_remote.api.LoginApiService
import com.sopt.now.data.dto.request.RequestLoginDto
import com.sopt.now.data_remote.dto.request.RequestSignUpDto
import com.sopt.now.data.dto.response.BaseResponse
import com.sopt.now.data.dto.response.ResponseMemberInfo
import retrofit2.Response
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val loginApiService: LoginApiService
) : LoginDataSource {

    override suspend fun signUp(request: RequestSignUpDto): BaseResponse<Unit> =
        loginApiService.signUp(request)

    override suspend fun login(request: RequestLoginDto): Response<BaseResponse<Unit>> =
        loginApiService.login(request)

    override suspend fun getMemberInfo(): BaseResponse<ResponseMemberInfo> =
        loginApiService.getMemberInfo()
}
