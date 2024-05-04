package com.sopt.now.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.LoginRepository
import com.sopt.now.domain.usecase.sharedprefusecase.ClearUserInfoUseCase
import com.sopt.now.domain.usecase.sharedprefusecase.SaveUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val saveUserIdUseCase: SaveUserIdUseCase,
    private val clearUserInfoUseCase: ClearUserInfoUseCase,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _memberProfileState = MutableStateFlow<UiState<UserEntity>>(UiState.Loading)
    val memberProfileState: StateFlow<UiState<UserEntity>> get() = _memberProfileState.asStateFlow()

    init {
        getMemberProfile()
    }

    fun updateCheckLoginState(isAutoLogin: Int) {
        saveUserIdUseCase.invoke(isAutoLogin)
    }

    fun clearSharedPrefUserInfo() = clearUserInfoUseCase.invoke()

    private fun getMemberProfile() {
        viewModelScope.launch {
            loginRepository.getMemberInfo()
                .onSuccess { state ->
                    _memberProfileState.emit(UiState.Success(state))
                }.onFailure { t ->
                    _memberProfileState.emit(UiState.Failure(t.message.toString()))
                }
        }
    }
}
