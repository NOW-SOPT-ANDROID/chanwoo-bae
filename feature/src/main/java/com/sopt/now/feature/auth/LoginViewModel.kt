package com.sopt.now.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.LoginRepository
import com.sopt.now.domain.usecase.sharedprefusecase.GetUserIdUseCase
import com.sopt.now.domain.usecase.sharedprefusecase.SaveUserIdUseCase
import com.sopt.now.feature.util.KeyStorage.HEADER_ID_DEFAULT_NUM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase,
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
        }
    }

    private fun isAutoLogin(): Boolean = getUserIdUseCase.invoke() != HEADER_ID_DEFAULT_NUM

    fun saveCheckLoginSharedPreference(input: Int) {
        viewModelScope.launch {
            saveUserIdUseCase.invoke(input)
        }
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
                    _postLoginState.emit(UiState.Failure("서버로부터 예상한 응답을 받지 못했습니다"))
                }
            }.onFailure {
                _postLoginState.emit(UiState.Failure(it.message.toString()))
            }
        }
    }
}
