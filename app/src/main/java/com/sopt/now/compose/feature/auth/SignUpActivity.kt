package com.sopt.now.compose.feature.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.now.compose.R
import com.sopt.now.compose.component.button.CustomButton
import com.sopt.now.compose.component.text.PageTitle
import com.sopt.now.compose.component.textfiled.CustomTextFieldWithTitle
import com.sopt.now.compose.core.context.toast
import com.sopt.now.compose.feature.model.User
import com.sopt.now.compose.feature.util.KeyStorage
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpScreen(
                        onSignUpBtnClick = { user ->
                            val validationMessages = validateUserInputData(user)
                            handleValidationResult(validationMessages, user)
                        }
                    )
                }
            }
        }
    }

    private fun validateUserInputData(user: User): List<String> {
        val validationMessages = mutableListOf<String>()

        checkIdLength(user, validationMessages)
        checkPwdLength(user, validationMessages)
        checkNickNameLength(user, validationMessages)
        checkMbtiLength(user, validationMessages)

        return validationMessages
    }

    private fun checkIdLength(
        user: User,
        validationMessages: MutableList<String>
    ) {
        validateLength(user.id, MIN_ID_LENGTH, MAX_ID_LENGTH, R.string.login_id_text)?.let {
            validationMessages.add(it)
        }
    }

    private fun checkPwdLength(
        user: User,
        validationMessages: MutableList<String>
    ) {
        validateLength(
            user.password,
            MIN_PWD_LENGTH,
            MAX_PWD_LENGTH,
            R.string.login_password_text
        )?.let {
            validationMessages.add(it)
        }
    }

    private fun checkMbtiLength(
        user: User,
        validationMessages: MutableList<String>
    ) {
        if (user.mbti.length != MBTI_LENGTH) {
            validationMessages.add(getString(R.string.sign_up_mbti_snack_message, MBTI_LENGTH))
        }
    }

    private fun checkNickNameLength(
        user: User,
        validationMessages: MutableList<String>
    ) {
        if (user.nickName.length <= MIN_NICKNAME_LENGTH) {
            validationMessages.add(
                getString(
                    R.string.sign_up_nickname_snack_message,
                    MIN_NICKNAME_LENGTH
                )
            )
        }
    }

    private fun validateLength(
        input: String,
        minLength: Int,
        maxLength: Int,
        fieldNameResId: Int
    ): String? {
        return if (input.length !in minLength..maxLength) {
            getString(
                R.string.sign_up_snack_message,
                getString(fieldNameResId),
                minLength,
                maxLength
            )
        } else {
            null
        }
    }

    private fun handleValidationResult(validationMessages: List<String>, userInputData: User) {
        if (validationMessages.isEmpty()) {
            navigateToLoginActivity(userInputData)
            toast("회원가입이 완료되었습니다.")
        } else {
            toast(validationMessages.joinToString("\n"))
        }
    }

    private fun navigateToLoginActivity(userInputData: User) {
        Intent().apply {
            putExtra(KeyStorage.USER_INPUT, userInputData)
            setResult(RESULT_OK, this)
            finish()
        }
    }

    companion object {
        const val MIN_ID_LENGTH = 6
        const val MAX_ID_LENGTH = 10
        const val MIN_PWD_LENGTH = 8
        const val MAX_PWD_LENGTH = 12
        const val MIN_NICKNAME_LENGTH = 2
        const val MBTI_LENGTH = 4
    }
}

@Composable
fun SignUpScreen(
    onSignUpBtnClick: (User) -> Unit
) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickName by remember { mutableStateOf("") }
    var mbti by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        topBar = {
            PageTitle("회원 가입 화면")
        },
        bottomBar = {
            CustomButton(
                text = "회원 가입 하기",
                buttonColor = Color(0xFF7AEBA8.toInt()),
                textColor = Color.Black,
                onClick = {
                    onSignUpBtnClick(
                        User(
                            id = id,
                            password = password,
                            nickName = nickName,
                            mbti = mbti
                        )
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextFieldWithTitle(
                title = "이메일",
                hint = "이메일을 입력해주세요",
                input = id,
                onInputChange = { id = it }
            )
            CustomTextFieldWithTitle(
                title = "비밀번호",
                hint = "비밀번호를 입력해주세요",
                input = password,
                onInputChange = { password = it }
            )
            CustomTextFieldWithTitle(
                title = "닉네임",
                hint = "닉네임을 입력해주세요",
                input = nickName,
                onInputChange = { nickName = it }
            )
            CustomTextFieldWithTitle(
                title = "MBTI",
                hint = "mbti를 입력해주세요",
                input = mbti,
                onInputChange = { mbti = it }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NOWSOPTAndroidTheme {
        SignUpScreen(
            onSignUpBtnClick = {}
        )
    }
}
