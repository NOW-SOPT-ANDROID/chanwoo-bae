package com.sopt.now.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.usecase.sharedprefusecase.SaveUserInfoUseCase
import com.sopt.now.feature.model.User
import com.sopt.now.feature.util.StringResources
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val saveUserInfoUseCase: SaveUserInfoUseCase
) : ViewModel() {

    private var _user = MutableStateFlow<User>(
        User(
            id = "",
            password = "",
            nickName = "",
            mbti = ""
        )
    )
    val user: StateFlow<User> get() = _user

    private var _signUpState = MutableSharedFlow<UiState<User>>()
    val signUpState: SharedFlow<UiState<User>> get() = _signUpState.asSharedFlow()

    fun setUser(user: User) {
        _user.value = user
        checkValidateUser()
    }

    fun saveUserInfoSharedPreference(input: UserEntity) {
        saveUserInfoUseCase.invoke(input)
    }

    private fun checkValidateUser() {
        viewModelScope.launch {
            _signUpState.emit(UiState.Loading)
            val newState = when {
                !idValidate() -> UiState.Failure(StringResources.ID_ERROR_MESSAGE)
                !pwdValidate() -> UiState.Failure(StringResources.PASSWORD_ERROR_MESSAGE)
                !nickNameValidate() -> UiState.Failure(StringResources.NICKNAME_ERROR_MESSAGE)
                !mbtiValidate() -> UiState.Failure(StringResources.MBTI_ERROR_MESSAGE)
                else -> UiState.Success(user.value)
            }
            _signUpState.emit(newState)
        }
    }

    private fun idValidate(): Boolean {
        return user.value.id.length in MIN_ID_LENGTH..MAX_ID_LENGTH
    }

    private fun pwdValidate(): Boolean {
        return user.value.password.length in MIN_PWD_LENGTH..MAX_PWD_LENGTH
    }

    private fun nickNameValidate(): Boolean {
        return user.value.nickName.length >= MIN_NICKNAME_LENGTH
    }

    private fun mbtiValidate(): Boolean {
        return mbtiRegex.matches(user.value.mbti)
    }

    companion object {
        const val MIN_ID_LENGTH = 6
        const val MAX_ID_LENGTH = 10
        const val MIN_PWD_LENGTH = 8
        const val MAX_PWD_LENGTH = 12
        const val MIN_NICKNAME_LENGTH = 1
        private const val MBTI_PATTERN = "^[E|Iei][S|Nsn][T|Ftf][J|Pjp]$"
        private val mbtiRegex = Regex(MBTI_PATTERN)
    }
}
