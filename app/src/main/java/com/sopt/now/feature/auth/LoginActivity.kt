package com.sopt.now.feature.auth

import android.content.Intent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.core.util.intent.getSafeParcelable
import com.sopt.now.core.util.intent.navigateTo
import com.sopt.now.core.view.UiState
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.feature.MainActivity
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
        initIsLoginCheck()
        initRegisterResultLauncher()
        initBtnClickListener()
        initSignUpStateObserve()
    }

    private fun initIsLoginCheck() {
        if (viewModel.isAutoLogin()) navigateTo<MainActivity>(this)
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
            viewModel.setLogin(
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

    private fun initSignUpStateObserve() {
        viewModel.loginState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    toast(getString(R.string.login_completed, getString(R.string.login)))
                    navigateToMainActivity(state.data)
                }

                is UiState.Failure -> snackBar(binding.root, state.errorMessage)
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToMainActivity(userData: UserEntity) {
        MainActivity.createIntent(
            this@LoginActivity,
            user = User(
                id = userData.id,
                password = userData.password,
                nickName = userData.nickName,
                mbti = userData.mbti
            )
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also {
            startActivity(it)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}
