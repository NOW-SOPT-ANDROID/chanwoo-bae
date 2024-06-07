package com.sopt.now.data_remote.datasource

import com.sopt.now.data_remote.dto.response.ResponseReqresDto

interface ReqresDataSource {
    suspend fun getReqresList(page: Int): ResponseReqresDto
}
