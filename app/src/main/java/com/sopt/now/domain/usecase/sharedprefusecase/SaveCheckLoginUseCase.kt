package com.sopt.now.domain.usecase.sharedprefusecase

import com.sopt.now.domain.repository.UserInfoRepository
import javax.inject.Inject

class SaveCheckLoginUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke(isChecked: Boolean) {
        userInfoRepository.saveCheckLogin(isChecked)
    }
}
