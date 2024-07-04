package com.sopt.now.compose.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data_api.auth.AuthRepository
import com.sopt.now.compose.model.ApiError
import com.sopt.now.compose.model.NetWorkConnectError
import com.sopt.now.compose.model.User
import com.sopt.now.compose.ui.core.view.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _signUpResponseState = MutableSharedFlow<UiState<User>>()
    val signUpResponseState: SharedFlow<UiState<User>> get() = _signUpResponseState.asSharedFlow()

    fun postSignUp(userEntity: User) {
        viewModelScope.launch {
            authRepository.signUp(userEntity)
                .onSuccess {
                    _signUpResponseState.emit(UiState.Success(userEntity))
                }.onFailure {
                    when (it) {
                        is ApiError, is NetWorkConnectError ->
                            _signUpResponseState.emit(UiState.Failure(it.message.toString()))
                    }
                }
        }
    }
}
