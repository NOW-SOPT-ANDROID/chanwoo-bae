package com.sopt.now.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.feature.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private var _savedUserInfo = MutableStateFlow<User>(
        User(
            id = "",
            password = "",
            nickName = "",
            mbti = ""
        )
    )
    val savedUserInfo: StateFlow<User> get() = _savedUserInfo

    private val _loginState = MutableSharedFlow<UiState<User>>()
    val loginState: SharedFlow<UiState<User>> get() = _loginState.asSharedFlow()

    fun setSavedUserInfo(input: User) {
        _savedUserInfo.value = input
    }

    fun setLogin(id: String, pwd: String) {
        checkLoginValidate(id, pwd)
    }

    private fun checkLoginValidate(id: String, pwd: String) {
        viewModelScope.launch {
            _loginState.emit(UiState.Loading)
            val newState = when {
                !isSavedUserInfo() -> UiState.Failure("회원가입을 먼저 진행해주세요.")
                !idPwdNullValidate(id, pwd) -> UiState.Failure("아이디와 비밀번호를 입력해주세요.")
                !idValidate(id) -> UiState.Failure("아이디가 일치하지 않습니다.")
                !pwdValidate(pwd) -> UiState.Failure("비밀번호가 일치하지 않습니다.")
                else -> UiState.Success(savedUserInfo.value)
            }
            _loginState.emit(newState)
        }
    }

    private fun isSavedUserInfo(): Boolean {
        return savedUserInfo.value.id.isNotEmpty() && savedUserInfo.value.password.isNotEmpty()
    }

    private fun idPwdNullValidate(id: String, pwd: String): Boolean {
        return id.isNotEmpty() && pwd.isNotEmpty()
    }

    private fun idValidate(id: String): Boolean {
        return id == savedUserInfo.value.id
    }

    private fun pwdValidate(pwd: String): Boolean {
        return pwd == savedUserInfo.value.password
    }
}
