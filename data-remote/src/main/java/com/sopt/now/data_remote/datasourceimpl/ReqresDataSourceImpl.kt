package com.sopt.now.data_remote.datasourceimpl

import android.net.http.NetworkException
import com.sopt.now.data.datasource.remote.ReqresDataSource
import com.sopt.now.data_remote.api.ReqresService
import com.sopt.now.data_remote.dto.response.ResponseReqresDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ReqresDataSourceImpl @Inject constructor(
    private val reqresService: ReqresService
) : ReqresDataSource {
    override suspend fun getReqresList(page: Int): ResponseReqresDto {
        return try {
            reqresService.getUserList(page)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun <T> handleException(e: Throwable): T {
        // 로그 또는 다른 오류 처리 로직을 여기에 추가할 수 있습니다.
        when (e) {
            is IOException -> {
                // 네트워크 오류 처리
                throw Exception("Network error occurred")
            }

            is HttpException -> {
                // HTTP 오류 처리
                throw Exception("API error occurred")
            }

            else -> {
                // 기타 오류 처리
                throw Exception("An unknown error occurred")
            }
        }
    }
}
