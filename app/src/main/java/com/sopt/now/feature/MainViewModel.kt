package com.sopt.now.feature

import androidx.lifecycle.ViewModel
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.usecase.ClearUserInfoUseCase
import com.sopt.now.domain.usecase.GetUserInfoUseCase
import com.sopt.now.domain.usecase.SaveCheckLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
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
