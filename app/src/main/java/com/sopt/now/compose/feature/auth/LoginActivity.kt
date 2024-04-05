package com.sopt.now.compose.feature.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.sopt.now.compose.core.intent.getSafeParcelable
import com.sopt.now.compose.feature.MainActivity
import com.sopt.now.compose.feature.model.User
import com.sopt.now.compose.feature.util.KeyStorage
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class LoginActivity : ComponentActivity() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var userData: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRegisterResultLauncher()
        setContent {
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginBtnClick = { id, pwd ->
                            handleLoginState(id, pwd)
                        },
                        onSignUpBtnClick = {
                            navigateToSignUp()
                        }
                    )
                }
            }
        }
    }

    private fun initRegisterResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val receivedUserInput =
                        result.data?.getSafeParcelable<User>(name = KeyStorage.USER_INPUT)
                    if (receivedUserInput != null) {
                        userData = receivedUserInput
                    }
                }
            }
    }

    private fun navigateToSignUp() {
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun handleLoginState(
        idTextField: String,
        pwdTextField: String
    ) {
        if (userData?.id.isNullOrEmpty() && userData?.password.isNullOrEmpty()) {
            toast(getString(R.string.login_first_sign_up_null))
            return
        }
        when {
            userData?.id != idTextField -> {
                toast(getString(R.string.login_not_id_equal))
            }

            userData?.password != pwdTextField -> {
                toast(getString(R.string.login_not_pwed_equal))
            }

            else -> {
                navigateToMainActivity(userData)
                toast(getString(R.string.login_completed))
            }
        }
    }

    private fun navigateToMainActivity(userData: User?) {
        Intent(this@LoginActivity, MainActivity::class.java).apply {
            putExtra(KeyStorage.USER_INPUT, userData)
        }.also {
            startActivity(it)
            finish()
        }
    }
}

@Composable
fun LoginScreen(
    onLoginBtnClick: (String, String) -> Unit,
    onSignUpBtnClick: () -> Unit
) {
    var id by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        topBar = {
            PageTitle("로그인 화면")
        },
        bottomBar = {
            Column {
                CustomButton(
                    text = "로그인 하기",
                    buttonColor = Color(0xFF60D5A4.toInt()),
                    textColor = Color.Black,
                    onClick = {
                        // 로그인 버튼 클릭 시
                        onLoginBtnClick(id, password)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                CustomButton(
                    text = "회원 가입 하기",
                    buttonColor = Color(0xFF7AEBA8.toInt()),
                    textColor = Color.Black,
                    onClick = onSignUpBtnClick
                )
            }
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NOWSOPTAndroidTheme {
        LoginScreen(
            onLoginBtnClick = { _, _ ->
            },
            onSignUpBtnClick = {}
        )
    }
}
