package com.sopt.now.compose.domain

import com.sopt.now.compose.data_api.reqres.ReqresRepository
import com.sopt.now.compose.model.ReqresEntity

class ReqresUseCase(
    private val reqresRepository: ReqresRepository
) {
    suspend operator fun invoke(page: Int): Result<List<ReqresEntity>> =
        reqresRepository.getReqresList(page)

}
