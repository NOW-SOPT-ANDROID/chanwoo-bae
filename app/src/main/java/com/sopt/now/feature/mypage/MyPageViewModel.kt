package com.sopt.now.feature.mypage

import androidx.lifecycle.ViewModel
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.usecase.sharedprefusecase.ClearUserInfoUseCase
import com.sopt.now.domain.usecase.sharedprefusecase.GetUserInfoUseCase
import com.sopt.now.domain.usecase.sharedprefusecase.SaveCheckLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val saveCheckLoginUseCase: SaveCheckLoginUseCase,
    private val clearUserInfoUseCase: ClearUserInfoUseCase
) : ViewModel() {

    fun getSharedPrefUserInfo(): UserEntity = getUserInfoUseCase.invoke()
    fun updateCheckLoginState(isAutoLogin: Boolean) {
        saveCheckLoginUseCase.invoke(isAutoLogin)
    }

    fun clearSharedPrefUserInfo() = clearUserInfoUseCase.invoke()
}
