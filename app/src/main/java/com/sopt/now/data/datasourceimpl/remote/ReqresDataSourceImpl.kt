package com.sopt.now.data.datasourceimpl.remote

import com.sopt.now.data.api.ReqresService
import com.sopt.now.data.datasource.remote.ReqresDataSource
import com.sopt.now.data.dto.remote.response.ResponseReqresDto
import javax.inject.Inject

// 실제 데이터 소스를 구현하는 클래스, 네트워크 호출을 통해 데이터를 가져오는 역할
class ReqresDataSourceImpl @Inject constructor(
    private val reqresService: ReqresService
) : ReqresDataSource {
    override suspend fun getReqresList(page: Int): ResponseReqresDto =
        reqresService.getUserList(page)
}
