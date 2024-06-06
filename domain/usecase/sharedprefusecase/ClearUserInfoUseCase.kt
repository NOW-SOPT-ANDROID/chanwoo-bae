package com.sopt.now.domain.usecase.sharedprefusecase

import com.sopt.now.domain.repository.UserInfoRepository
import javax.inject.Inject

class ClearUserInfoUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke() {
        userInfoRepository.clear()
    }
}
