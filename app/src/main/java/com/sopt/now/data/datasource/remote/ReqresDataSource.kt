package com.sopt.now.data.datasource.remote

import com.sopt.now.data.dto.remote.response.ResponseReqresDto

// 레포지터리 인터페이스, 데이터 접근을 추상화, 데이터를 요청하는 코드를 부름
interface ReqresDataSource {
    suspend fun getReqresList(page: Int): ResponseReqresDto
}
