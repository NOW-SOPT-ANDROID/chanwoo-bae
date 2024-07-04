package com.sopt.now.data.repositoryimpl

import com.sopt.now.data.datasource.remote.ReqresDataSource
import com.sopt.now.data.mapper.toReqresList
import com.sopt.now.domain.entity.ReqresEntity
import com.sopt.now.domain.repository.ReqresRepository
import javax.inject.Inject

class ReqresRepositoryImpl @Inject constructor(
    private val reqresDataSource: ReqresDataSource
) : ReqresRepository {
    override suspend fun getReqresList(page: Int): Result<List<ReqresEntity>> = runCatching {
        reqresDataSource.getReqresList(page).toReqresList()
    }
}
