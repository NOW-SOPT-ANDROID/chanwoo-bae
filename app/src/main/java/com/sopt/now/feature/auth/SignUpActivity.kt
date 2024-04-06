package com.sopt.now.feature.auth

import android.content.Intent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.core.view.UiState
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.feature.model.User
import com.sopt.now.feature.util.KeyStorage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun initView() {
        initSignUpBtnClickListener()
        initSignUpStateObserve()
    }

    private fun initSignUpBtnClickListener() {
        binding.btnSignUp.setOnClickListener {
            viewModel.setUser(getUserInputData())
        }
    }

    private fun getUserInputData(): User {
        return with(binding) {
            User(
                id = etSignUpId.text.toString(),
                password = etSignUpPwd.text.toString(),
                nickName = etSignUpNickname.text.toString(),
                mbti = etSignUpMbti.text.toString()
            )
        }
    }

    private fun initSignUpStateObserve() {
        viewModel.signUpState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    toast("회원가입이 완료되었습니다.")
                    navigateToLoginActivity(state.data)
                }

                is UiState.Failure -> {
                    snackBar(binding.root, state.errorMessage)
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToLoginActivity(userInputData: User) {
        Intent().apply {
            putExtra(KeyStorage.USER_INPUT, userInputData)
        }.also {
            setResult(RESULT_OK, it)
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}
