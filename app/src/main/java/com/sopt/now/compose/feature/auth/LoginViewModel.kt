package com.sopt.now.compose.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data.api.UserIdProvider
import com.sopt.now.compose.data_api.auth.AuthRepository
import com.sopt.now.compose.feature.util.KeyStorage.ID_DEFAULT
import com.sopt.now.compose.ui.core.view.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginResponseState = MutableSharedFlow<UiState<Int>>()
    val loginResponseState: SharedFlow<UiState<Int>> get() = _loginResponseState.asSharedFlow()

    fun postLogin(id: String, pwd: String) {
        viewModelScope.launch {
            authRepository.login(id, pwd)
                .onSuccess { userId ->
                    UserIdProvider.setUserId(userId ?: ID_DEFAULT)
                    _loginResponseState.emit(UiState.Success(userId ?: ID_DEFAULT))
                }.onFailure {
                    _loginResponseState.emit(UiState.Failure(it.message.toString()))
                }
        }
    }
}
