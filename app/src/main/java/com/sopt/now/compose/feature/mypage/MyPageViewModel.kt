package com.sopt.now.compose.feature.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.data_api.auth.AuthRepository
import com.sopt.now.compose.model.User
import com.sopt.now.compose.ui.core.view.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _getMemberInfoState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val getMemberInfoState: StateFlow<UiState<User>> get() = _getMemberInfoState.asStateFlow()

    init {
        getMemberInfo()
    }

    private fun getMemberInfo() {
        viewModelScope.launch {
            authRepository.getMemberInfo()
                .onSuccess {
                    _getMemberInfoState.emit(UiState.Success(it))
                }.onFailure {
                    _getMemberInfoState.emit(UiState.Failure(it.message.toString()))

                }
        }
    }
}
