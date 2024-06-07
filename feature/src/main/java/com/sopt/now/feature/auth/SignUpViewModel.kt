package com.sopt.now.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.domain.entity.ApiError
import com.sopt.now.domain.entity.NetWorkConnectError
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.LoginRepository
import com.sopt.now.domain.usecase.regex.PasswordValidationUseCase
import com.sopt.now.domain.usecase.regex.PasswordValidationUseCase.Companion.PASSWORD_CORRECT
import com.sopt.now.domain.usecase.regex.PhoneNumberValidationUseCase
import com.sopt.now.feature.util.StringResources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val phoneNumberValidationUseCase: PhoneNumberValidationUseCase,
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity>(
        UserEntity(
            id = "",
            password = "",
            nickName = "",
            phone = "",
        ),
    )
    val user: StateFlow<UserEntity> get() = _user

    private val _signUpState = MutableSharedFlow<UiState<UserEntity>>()
    val signUpState: SharedFlow<UiState<UserEntity>> get() = _signUpState.asSharedFlow()

    private val _signUpResponseState = MutableSharedFlow<UiState<Boolean>>()
    val signUpResponseState get() = _signUpResponseState.asSharedFlow()

    fun setUser(user: UserEntity) {
        _user.value = user
        checkValidateUser(user)
    }

    private fun checkValidateUser(userEntity: UserEntity) {
        viewModelScope.launch {
            val pwdValidationResult =
                passwordValidationUseCase.invoke(user.value.password.orEmpty())
            val newState = when {
                !idValidate() -> UiState.Failure(StringResources.ID_ERROR_MESSAGE)
                !pwdValidate(pwdValidationResult) -> UiState.Failure(pwdValidationResult)
                !nickNameValidate() -> UiState.Failure(StringResources.NICKNAME_ERROR_MESSAGE)
                !phoneNumberValidate() -> UiState.Failure(StringResources.MBTI_ERROR_MESSAGE)
                else -> UiState.Success(userEntity)
            }
            _signUpState.emit(newState)
        }
    }

    fun postSignUp(userEntity: UserEntity) {
        viewModelScope.launch {
            _signUpState.emit(UiState.Loading)
            loginRepository.signUp(userEntity)
                .onSuccess {
                    _signUpResponseState.emit(UiState.Success(true))
                }.onFailure {
                    when (it) {
                        is ApiError, is NetWorkConnectError ->
                            _signUpResponseState.emit(UiState.Failure(it.message.toString()))
                    }
                }
        }
    }

    private fun idValidate(): Boolean =
        user.value.id.length in MIN_ID_LENGTH..MAX_ID_LENGTH

    private fun pwdValidate(pwdValidationResult: String): Boolean =
        pwdValidationResult == PASSWORD_CORRECT

    private fun nickNameValidate(): Boolean =
        user.value.nickName.length >= MIN_NICKNAME_LENGTH

    private fun phoneNumberValidate(): Boolean =
        phoneNumberValidationUseCase.invoke(user.value.phone)

    companion object {
        const val MIN_ID_LENGTH = 6
        const val MAX_ID_LENGTH = 10
        const val MIN_NICKNAME_LENGTH = 1
    }
}
