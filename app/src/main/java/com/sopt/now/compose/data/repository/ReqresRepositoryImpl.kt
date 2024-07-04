package com.sopt.now.compose.data.repository

import com.sopt.now.compose.data.api.service.ReqresService
import com.sopt.now.compose.data.mapper.toReqresData
import com.sopt.now.compose.data_api.reqres.ReqresRepository
import com.sopt.now.compose.model.ReqresEntity

internal class ReqresRepositoryImpl(
    private val reqresService: ReqresService
) : ReqresRepository {
    override suspend fun getReqresList(page: Int): Result<List<ReqresEntity>> = runCatching {
        reqresService.getUserList(page).toReqresData()
    }
}
