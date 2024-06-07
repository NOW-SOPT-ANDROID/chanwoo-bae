package com.sopt.now.data_remote.api

import com.sopt.now.data_remote.dto.response.ResponseReqresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("api/users")
    suspend fun getUserList(
        @Query("page") page: Int
    ): ResponseReqresDto
}
