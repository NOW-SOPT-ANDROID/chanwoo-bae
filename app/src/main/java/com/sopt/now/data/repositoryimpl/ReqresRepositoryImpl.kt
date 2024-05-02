package com.sopt.now.data.repositoryimpl

import com.sopt.now.data.datasource.remote.ReqresDataSource
import com.sopt.now.domain.entity.ReqresEntity
import com.sopt.now.domain.repository.ReqresRepository
import javax.inject.Inject

// 외부 데이터 소스(ReqresDataSource)로부터 데이터를 가져오고, 필요한 형태로 변환하여 비즈니스 로직 계층에게 제공
class ReqresRepositoryImpl @Inject constructor(
    private val reqresDataSource: ReqresDataSource
) : ReqresRepository {
    override suspend fun getReqresList(page: Int): Result<List<ReqresEntity>> = runCatching {
        reqresDataSource.getReqresList(page).toReqresList()
    }
}
