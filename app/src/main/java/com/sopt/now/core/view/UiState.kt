package com.sopt.now.core.view

sealed interface UiState<out T> {

    object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T
    ) : UiState<T>

    object Error : UiState<Nothing>
}
