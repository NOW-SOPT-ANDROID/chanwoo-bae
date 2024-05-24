package com.sopt.now.data.api

import com.sopt.now.data.dto.remote.response.ResponseReqresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("api/users")
    suspend fun getUserList(
        @Query("page") page: Int
    ): ResponseReqresDto
}
