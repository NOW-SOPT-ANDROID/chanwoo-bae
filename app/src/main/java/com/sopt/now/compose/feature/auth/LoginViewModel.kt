package com.sopt.now.compose.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ui.core.view.UiState
import com.sopt.now.compose.data.api.ApiFactory
import com.sopt.now.compose.data.api.UserIdProvider
import com.sopt.now.compose.data.api.dto.request.RequestLoginDto
import com.sopt.now.compose.feature.util.KeyStorage.ID_DEFAULT
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel : ViewModel() {
    private val _loginResponseState = MutableSharedFlow<UiState<Int>>()
    val loginResponseState: SharedFlow<UiState<Int>> get() = _loginResponseState.asSharedFlow()

    fun postLogin(id: String, pwd: String) {
        viewModelScope.launch {
            runCatching {
                ApiFactory.ServicePool.authService.login(RequestLoginDto(id, pwd))
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    val userId = response.headers()["location"]
                    UserIdProvider.setUserId(userId?.toInt() ?: ID_DEFAULT)
                    _loginResponseState.emit(UiState.Success(userId?.toInt() ?: ID_DEFAULT))
                } else {
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody.toString())
                    val errorMessage = jsonObject.getString("message")

                    _loginResponseState.emit(UiState.Failure(errorMessage.toString()))
                }
            }.onFailure { throwable ->
                _loginResponseState.emit(UiState.Failure(throwable.message.toString()))
            }
        }
    }
}
