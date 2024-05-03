package com.sopt.now.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sopt.now.core.view.UiState
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.domain.repository.LoginRepository
import com.sopt.now.domain.usecase.regex.PasswordValidationUseCase
import com.sopt.now.domain.usecase.regex.PhoneNumberValidationUseCase
import com.sopt.now.domain.usecase.sharedprefusecase.SaveUserInfoUseCase
import com.sopt.now.feature.util.StringResources
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val saveUserInfoUseCase: SaveUserInfoUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val phoneNumberValidationUseCase: PhoneNumberValidationUseCase,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity>(
        UserEntity(
            id = "",
            password = "",
            nickName = "",
            phone = ""
        )
    )
    val user: StateFlow<UserEntity> get() = _user

    private val _signUpState = MutableSharedFlow<UiState<UserEntity>>()
    val signUpState: SharedFlow<UiState<UserEntity>> get() = _signUpState.asSharedFlow()

    private val _signUpResponseState = MutableSharedFlow<UiState<Boolean>>()
    val signUpResponseState: SharedFlow<UiState<Boolean>> get() = _signUpResponseState.asSharedFlow()

    fun setUser(user: UserEntity) {
        _user.value = user
        checkValidateUser(user)
    }

    fun saveUserInfoSharedPreference(input: UserEntity) {
        saveUserInfoUseCase.invoke(input)
    }

    private fun checkValidateUser(userEntity: UserEntity) {
        viewModelScope.launch {
            val newState = when {
                !idValidate() -> UiState.Failure(StringResources.ID_ERROR_MESSAGE)
                !pwdValidate() -> UiState.Failure(StringResources.PASSWORD_ERROR_MESSAGE)
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
                    if (it.code == 201) {
                        _signUpResponseState.emit(UiState.Success(true))
                    } else {
                        _signUpResponseState.emit(UiState.Failure(it.message))
                    }
                }.onFailure { throwable ->
                    if (throwable is HttpException && throwable.code() == 400) {
                        val errorBody = throwable.response()?.errorBody()?.string()
                        val jsonObject = JSONObject(errorBody)
                        val errorMessage = jsonObject.getString("message")
                        _signUpState.emit(UiState.Failure(errorMessage.toString()))
                    }
                }
        }
    }

    private fun idValidate(): Boolean =
        user.value.id.length in MIN_ID_LENGTH..MAX_ID_LENGTH

    private fun pwdValidate(): Boolean =
        passwordValidationUseCase.invoke(user.value.password ?: "")

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
