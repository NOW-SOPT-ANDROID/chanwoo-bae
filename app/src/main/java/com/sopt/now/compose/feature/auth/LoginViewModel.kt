package com.sopt.now.compose.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.core.view.UiState
import com.sopt.now.compose.data.api.ApiFactory
import com.sopt.now.compose.data.request.RequestLoginDto
import com.sopt.now.compose.feature.model.User
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel : ViewModel() {

    private val _user = MutableStateFlow<User>(
        User(
            id = "",
            password = "",
            nickName = "",
            phone = ""
        )
    )
    val user: StateFlow<User> get() = _user

    private val _signUpState = MutableSharedFlow<UiState<User>>()
    val signUpState: SharedFlow<UiState<User>> get() = _signUpState.asSharedFlow()

    private val _loginResponseState = MutableSharedFlow<UiState<Int>>()
    val loginResponseState: SharedFlow<UiState<Int>> get() = _loginResponseState.asSharedFlow()

    fun setUser(user: User) {
        _user.value = user
    }

    fun postLogin(id: String, pwd: String) {
        viewModelScope.launch {
            runCatching {
                ApiFactory.ServicePool.authService.login(RequestLoginDto(id, pwd))
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    val userId = response.headers()["location"]
                    _loginResponseState.emit(UiState.Success(userId?.toInt() ?: -1))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody.toString())
                    val errorMessage = jsonObject.getString("message")

                    _loginResponseState.emit(UiState.Failure(errorMessage.toString()))
                }
            }.onFailure { throwable ->
                _signUpState.emit(UiState.Failure(throwable.message.toString()))
            }
        }
    }
}
