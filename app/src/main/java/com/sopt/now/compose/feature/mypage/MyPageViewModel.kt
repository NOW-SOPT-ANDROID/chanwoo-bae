package com.sopt.now.compose.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.core.view.UiState
import com.sopt.now.compose.data.api.ApiFactory
import com.sopt.now.compose.feature.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class MyPageViewModel : ViewModel() {

    private val _getMemberInfoState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val getMemberInfoState: StateFlow<UiState<User>> get() = _getMemberInfoState.asStateFlow()

    init {
        getMemberInfo()
    }

    private fun getMemberInfo() {
        viewModelScope.launch {
            runCatching {
                ApiFactory.ServicePool.authService.getMemberInfo()
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    data?.let { _getMemberInfoState.emit(UiState.Success(it.toMemberInfo())) }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val jsonObject = JSONObject(errorBody.toString())
                    val errorMessage = jsonObject.getString("message")

                    _getMemberInfoState.emit(UiState.Failure(errorMessage.toString()))
                }
            }.onFailure { throwable ->
                _getMemberInfoState.emit(UiState.Failure(throwable.message.toString()))
            }
        }
    }
}
