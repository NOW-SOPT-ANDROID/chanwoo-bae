package com.sopt.now.feature.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.LoginRepository
import com.sopt.now.domain.usecase.sharedprefusecase.GetUserIdUseCase
import com.sopt.now.domain.usecase.sharedprefusecase.GetUserInfoUseCase
import com.sopt.now.domain.usecase.sharedprefusecase.SaveCheckLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val saveCheckLoginUseCase: SaveCheckLoginUseCase,
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _savedUserInfo = MutableStateFlow<UserEntity>(
        UserEntity(
            id = "",
            password = "",
            nickName = "",
            phone = ""
        )
    )
    val savedUserInfo: StateFlow<UserEntity> get() = _savedUserInfo

    private val _autoLoginState = MutableStateFlow<Boolean>(false)
    val autoLoginState: StateFlow<Boolean> get() = _autoLoginState.asStateFlow()

    private val _postLoginState = MutableSharedFlow<UiState<Int>>()
    val postLoginState: SharedFlow<UiState<Int>> get() = _postLoginState.asSharedFlow()

    init {
        checkAutoLogin()
    }

    private fun checkAutoLogin() {
        viewModelScope.launch {
            _autoLoginState.value = isAutoLogin()
            if (isAutoLogin()) {
                _savedUserInfo.value = getUserInfoUseCase.invoke()
            }
        }
    }

    private fun isAutoLogin(): Boolean = getUserIdUseCase.invoke() != -1

    fun saveCheckLoginSharedPreference(input: Int) {
        saveCheckLoginUseCase.invoke(input)
    }

    fun setSavedUserInfo(input: UserEntity) {
        _savedUserInfo.value = input
    }

    fun postLogin(id: String, pwd: String) {
        viewModelScope.launch {
            _postLoginState.emit(UiState.Loading)
            loginRepository.login(id, pwd).onSuccess { response ->
                if (response != null) {
                    _postLoginState.emit(UiState.Success(response))
                } else {
                    _postLoginState.emit(UiState.Failure("잘못 된 아이디, 비밀번호 입니다."))
                }
            }.onFailure {
                _postLoginState.emit(UiState.Failure(it.message.toString()))
                Log.d("errrormsg", it.message.toString())
            }
        }
    }
}
