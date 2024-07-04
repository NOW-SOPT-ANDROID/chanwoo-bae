package com.sopt.now.feature.auth

import android.content.Intent
import android.view.MotionEvent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.core_ui.base.BindingActivity
import com.sopt.now.core_ui.util.context.hideKeyboardOnTouch
import com.sopt.now.core_ui.util.context.snackBar
import com.sopt.now.core_ui.util.context.toast
import com.sopt.now.core_ui.util.intent.getSafeParcelable
import com.sopt.now.core_ui.util.intent.navigateTo
import com.sopt.now.core_ui.view.UiState
import com.sopt.now.feature.MainActivity
import com.sopt.now.feature.R
import com.sopt.now.feature.databinding.ActivityLoginBinding
import com.sopt.now.feature.model.User
import com.sopt.now.feature.util.KeyStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val viewModel by viewModels<LoginViewModel>()

    override fun initView() {
        initAutoLoginStateObserve()
        initRegisterResultLauncher()
        initBtnClickListener()
        initPostLoginStateObserve()
    }

    private fun initAutoLoginStateObserve() {
        viewModel.autoLoginState.flowWithLifecycle(lifecycle).onEach { isAutoLogin ->
            when (isAutoLogin) {
                true -> navigateTo<MainActivity>(this@LoginActivity)
                false -> return@onEach
            }
        }.launchIn(lifecycleScope)
    }

    private fun initRegisterResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.getSafeParcelable<User>(name = KeyStorage.USER_INPUT)
                        ?.let { receivedUserInput ->
                            viewModel.setSavedUserInfo(receivedUserInput.toUserEntity())
                        }
                }
            }
    }

    private fun initBtnClickListener() {
        initLoginBtnClickListener()
        initSignUpBtnClickListener()
    }

    private fun initLoginBtnClickListener() = with(binding) {
        btnLogin.setOnClickListener {
            viewModel.postLogin(
                id = etLoginId.text.toString(),
                pwd = etLoginPwd.text.toString()
            )
        }
    }

    private fun initSignUpBtnClickListener() {
        binding.tvLoginSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun initPostLoginStateObserve() {
        viewModel.postLoginState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    toast("로그인이 완료되었고 id는 ${state.data} 입니다")
                    viewModel.saveCheckLoginSharedPreference(state.data)
                    navigateTo<MainActivity>(this)
                }

                is UiState.Failure -> snackBar(binding.root, state.errorMessage)
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        hideKeyboardOnTouch(ev)
        return super.dispatchTouchEvent(ev)
    }
}
