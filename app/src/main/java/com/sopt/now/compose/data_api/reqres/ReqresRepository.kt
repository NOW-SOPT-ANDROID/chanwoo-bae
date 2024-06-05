package com.sopt.now.compose.data_api.reqres

import com.sopt.now.compose.model.ReqresEntity

interface ReqresRepository {
    suspend fun getReqresList(page: Int): Result<List<ReqresEntity>>
}
