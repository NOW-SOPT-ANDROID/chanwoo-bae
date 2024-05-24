package com.sopt.now.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.domain.entity.ReqresEntity
import com.sopt.now.domain.repository.ReqresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val reqresRepository: ReqresRepository
) : ViewModel() {
    private val _reqresUserState = MutableStateFlow<UiState<List<ReqresEntity>>>(UiState.Loading)
    val reqresUserState get() = _reqresUserState.asStateFlow()

    init {
        getReqresUserList(1)
    }

    private fun getReqresUserList(page: Int) {
        viewModelScope.launch {
            _reqresUserState.emit(UiState.Loading)
            reqresRepository.getReqresList(page)
                .onSuccess {
                    if (it.isNotEmpty()) _reqresUserState.emit(UiState.Success(it.toList()))
                }.onFailure {
                    _reqresUserState.emit(UiState.Failure(it.message.toString()))
                }
        }
    }
}
