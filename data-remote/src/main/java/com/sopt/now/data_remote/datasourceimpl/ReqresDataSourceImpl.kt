package com.sopt.now.data_remote.datasourceimpl

import com.sopt.now.data_remote.api.ReqresService
import com.sopt.now.data_remote.datasource.ReqresDataSource
import com.sopt.now.data_remote.dto.response.ResponseReqresDto
import javax.inject.Inject

class ReqresDataSourceImpl @Inject constructor(
    private val reqresService: ReqresService
) : ReqresDataSource {
    override suspend fun getReqresList(page: Int): ResponseReqresDto =
        reqresService.getUserList(page)
}
