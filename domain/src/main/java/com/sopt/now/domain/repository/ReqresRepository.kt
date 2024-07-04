package com.sopt.now.domain.repository

import com.sopt.now.domain.entity.ReqresEntity

interface ReqresRepository {
    suspend fun getReqresList(page: Int): Result<List<ReqresEntity>>
}
