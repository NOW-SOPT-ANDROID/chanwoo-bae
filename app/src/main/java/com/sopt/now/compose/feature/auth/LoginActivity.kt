package com.sopt.now.compose.feature.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sopt.now.compose.feature.MainActivity
import com.sopt.now.compose.feature.util.KeyStorage
import com.sopt.now.compose.model.User
import com.sopt.now.compose.ui.component.button.CustomButton
import com.sopt.now.compose.ui.component.text.PageTitle
import com.sopt.now.compose.ui.component.textfiled.CustomTextFieldWithTitle
import com.sopt.now.compose.ui.core.factory.ViewModelFactory
import com.sopt.now.compose.ui.core.intent.getSafeParcelable
import com.sopt.now.compose.ui.core.view.UiState
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginActivity : ComponentActivity() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var userData: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRegisterResultLauncher()
        setContent {
            val viewModel: LoginViewModel = viewModel(factory = ViewModelFactory())
            NOWSOPTAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginBtnClick = { id, pwd ->
                            viewModel.postLogin(id, pwd)
                        },
                        onSignUpBtnClick = {
                            navigateToSignUp()
                        },
                        viewModel
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
}

@Composable
fun LoginScreen(
    onLoginBtnClick: (String, String) -> Unit,
    onSignUpBtnClick: () -> Unit,
    viewModel: LoginViewModel
) {
    var id by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val composeLifecycle = LocalLifecycleOwner.current

    LaunchedEffect(composeLifecycle) {
        viewModel.loginResponseState.flowWithLifecycle(lifecycle = composeLifecycle.lifecycle)
            .onEach { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        Toast.makeText(context, uiState.data.toString(), Toast.LENGTH_SHORT).show()
                        navigateToMainActivity(context, uiState.data)
                    }

                    is UiState.Failure -> {
                        Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }.launchIn(composeLifecycle.lifecycleScope)
    }

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

private fun navigateToMainActivity(context: Context, data: Int) {
    val currentContext = context as ComponentActivity
    Intent(currentContext, MainActivity::class.java).apply {
        putExtra(KeyStorage.USER_INPUT, data)
    }.also {
        currentContext.startActivity(it)
        currentContext.finish()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NOWSOPTAndroidTheme {
    }
}
