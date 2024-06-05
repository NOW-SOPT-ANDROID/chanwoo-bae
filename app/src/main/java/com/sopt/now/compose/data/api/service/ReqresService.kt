package com.sopt.now.compose.data.api.service

import com.sopt.now.compose.data.api.dto.response.ReqresResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("api/users")
    suspend fun getUserList(
        @Query("page") page: Int
    ): ReqresResponse
}
