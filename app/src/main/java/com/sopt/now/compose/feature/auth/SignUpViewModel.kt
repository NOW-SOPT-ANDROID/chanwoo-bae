package com.sopt.now.compose.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.core.view.UiState
import com.sopt.now.compose.data.api.ApiFactory
import com.sopt.now.compose.data.request.RequestSignUpDto
import com.sopt.now.compose.feature.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class SignUpViewModel : ViewModel() {

    private val _signUpResponseState = MutableSharedFlow<UiState<User>>()
    val signUpResponseState: SharedFlow<UiState<User>> get() = _signUpResponseState.asSharedFlow()

    fun setUser(user: User) {
        postSignUp(user)
    }

    fun postSignUp(userEntity: User) {
        viewModelScope.launch {
            runCatching {
                ApiFactory.ServicePool.authService.signUp(RequestSignUpDto.toSignUpDto(userEntity))
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    _signUpResponseState.emit(UiState.Success(userEntity))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody.toString())
                    val errorMessage = jsonObject.getString("message")

                    _signUpResponseState.emit(UiState.Failure(errorMessage.toString()))
                }
            }.onFailure { throwable ->
                _signUpResponseState.emit(UiState.Failure(throwable.message.toString()))
            }
        }
    }
}
