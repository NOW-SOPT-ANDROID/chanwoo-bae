package com.sopt.now.compose.feature.auth

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.component.button.CustomButton
import com.sopt.now.compose.component.text.PageTitle
import com.sopt.now.compose.component.textfiled.CustomTextFieldWithTitle
import com.sopt.now.compose.core.view.UiState
import com.sopt.now.compose.feature.model.User
import com.sopt.now.compose.feature.util.KeyStorage
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class SignUpActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SignUpViewModel = viewModel()
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUpScreen(
                        onSignUpBtnClick = { user -> viewModel.postSignUp(user) },
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    onSignUpBtnClick: (User) -> Unit,
    viewModel: SignUpViewModel
) {
    var id by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var nickName by rememberSaveable { mutableStateOf("") }
    var mbti by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val composeLifecycle = LocalLifecycleOwner.current.lifecycle


    LaunchedEffect(composeLifecycle) {
        viewModel.signUpResponseState.flowWithLifecycle(lifecycle = composeLifecycle)
            .collect { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        Toast.makeText(context, uiState.data.toString(), Toast.LENGTH_SHORT).show()
                        navigateToLoginActivity(context, uiState.data)
                    }

                    is UiState.Failure -> {
                        Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
    }

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
                            phone = mbti
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

private fun navigateToLoginActivity(context: Context, data: User) {
    val currentContext = context as ComponentActivity
    Intent().apply {
        putExtra(KeyStorage.USER_INPUT, data)
        currentContext.setResult(RESULT_OK)
        currentContext.finish()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    NOWSOPTAndroidTheme {
        /* SignUpScreen(
             onSignUpBtnClick = {}
         )*/
    }
}
