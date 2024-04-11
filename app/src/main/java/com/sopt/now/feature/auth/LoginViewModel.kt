package com.sopt.now.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.usecase.sharedprefusecase.GetCheckLoginUseCase
import com.sopt.now.domain.usecase.sharedprefusecase.GetUserInfoUseCase
import com.sopt.now.domain.usecase.sharedprefusecase.SaveCheckLoginUseCase
import com.sopt.now.feature.util.StringResources
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getCheckLoginUseCase: GetCheckLoginUseCase
) : ViewModel() {
    private var _savedUserInfo = MutableStateFlow<UserEntity>(
        UserEntity(
            id = "",
            password = "",
            nickName = "",
            mbti = ""
        )
    )
    val savedUserInfo: StateFlow<UserEntity> get() = _savedUserInfo

    private val _loginState = MutableSharedFlow<UiState<UserEntity>>()
    val loginState: SharedFlow<UiState<UserEntity>> get() = _loginState.asSharedFlow()

    init {
        if (!isAutoLogin()) _savedUserInfo.value = getUserInfoUseCase.invoke()
    }

    fun isAutoLogin(): Boolean = getCheckLoginUseCase.invoke()

    fun setSavedUserInfo(input: UserEntity) {
        _savedUserInfo.value = input
    }

    fun setLogin(id: String, pwd: String) {
        checkLoginValidate(id, pwd)
    }

    private fun checkLoginValidate(id: String, pwd: String) {
        viewModelScope.launch {
            _loginState.emit(UiState.Loading)
            val newState = when {
                !isSavedUserInfo() -> UiState.Failure(StringResources.SIGN_UP_REQUIRED)
                !idPwdNullValidate(id, pwd) -> UiState.Failure(StringResources.ID_PASSWORD_REQUIRED)
                !idValidate(id) -> UiState.Failure(StringResources.INVALID_ID)
                !pwdValidate(pwd) -> UiState.Failure(StringResources.INVALID_PASSWORD)
                else -> UiState.Success(savedUserInfo.value)
            }
            _loginState.emit(newState)
        }
    }

    private fun isSavedUserInfo(): Boolean =
        savedUserInfo.value.id.isNotEmpty() && savedUserInfo.value.password.isNotEmpty()

    private fun idPwdNullValidate(id: String, pwd: String): Boolean =
        id.isNotEmpty() && pwd.isNotEmpty()

    private fun idValidate(id: String): Boolean =
        id == savedUserInfo.value.id

    private fun pwdValidate(pwd: String): Boolean =
        pwd == savedUserInfo.value.password
}
