package com.sopt.now.compose.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.ui.core.view.UiState
import com.sopt.now.compose.data.api.ApiFactory
import com.sopt.now.compose.feature.model.ReqresEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _getReqresListState = MutableStateFlow<UiState<List<ReqresEntity>>>(UiState.Loading)
    val getReqresListState: StateFlow<UiState<List<ReqresEntity>>>
        get() = _getReqresListState.asStateFlow()

    fun getReqresList(page: Int) {
        viewModelScope.launch {
            runCatching {
                ApiFactory.ServicePool.userService.getUserList(page)
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    val reqresUserList = response.body()?.toReqresList()
                    if (reqresUserList != null) {
                        _getReqresListState.emit(UiState.Success(reqresUserList))
                    } else {
                        _getReqresListState.emit(UiState.Failure("null"))
                    }
                }
            }.onFailure { throwable ->
                _getReqresListState.emit(UiState.Failure(throwable.message.toString()))
            }
        }
    }
}
