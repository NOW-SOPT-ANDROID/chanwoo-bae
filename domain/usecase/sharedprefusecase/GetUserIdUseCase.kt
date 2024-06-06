package com.sopt.now.domain.usecase.sharedprefusecase

import com.sopt.now.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke(): Int {
        return userInfoRepository.getCheckLogin()
    }
}
