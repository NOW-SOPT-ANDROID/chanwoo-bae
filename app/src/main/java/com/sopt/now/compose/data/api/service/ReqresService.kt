package com.sopt.now.compose.data.api.service

import com.sopt.now.compose.data.response.ResponseReqresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("api/users")
    suspend fun getUserList(
        @Query("page") page: Int
    ): ResponseReqresDto
}
