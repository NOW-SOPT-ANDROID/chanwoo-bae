package com.sopt.now.compose.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.compose.domain.ReqresUseCase
import com.sopt.now.compose.model.ReqresEntity
import com.sopt.now.compose.ui.core.view.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val reqresUseCase: ReqresUseCase
) : ViewModel() {

    private val _getReqresListState = MutableStateFlow<UiState<List<ReqresEntity>>>(UiState.Loading)
    val getReqresListState: StateFlow<UiState<List<ReqresEntity>>>
        get() = _getReqresListState.asStateFlow()

    fun getReqresList(page: Int) {
        viewModelScope.launch {
            reqresUseCase.invoke(page)
                .onSuccess { result ->
                    _getReqresListState.emit(UiState.Success(result))
                }.onFailure {
                    _getReqresListState.emit(UiState.Failure(it.message.toString()))
                }
        }
    }
}
