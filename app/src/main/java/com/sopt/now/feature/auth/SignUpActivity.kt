package com.sopt.now.feature.auth

import android.content.Intent
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.hideKeyboardOnTouch
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.core.view.UiState
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.domain.entity.UserEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun initView() {
        initSignUpBtnClickListener()
        initSignUpStateObserve()
        initSignUpResponseStateObserve()
    }

    private fun initSignUpBtnClickListener() {
        binding.btnSignUp.setOnClickListener {
            viewModel.setUser(getUserInputData())
        }
    }

    private fun getUserInputData(): UserEntity {
        return with(binding) {
            UserEntity(
                id = etSignUpId.text.toString(),
                password = etSignUpPwd.text.toString(),
                nickName = etSignUpNickname.text.toString(),
                phone = etSignUpMbti.text.toString()
            )
        }
    }

    private fun initSignUpStateObserve() {
        viewModel.signUpState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> viewModel.postSignUp(state.data)
                is UiState.Failure -> snackBar(binding.root, state.errorMessage)
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun initSignUpResponseStateObserve() {
        viewModel.signUpResponseState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> {
                    toast(getString(R.string.login_completed, getString(R.string.sign_up)))
                    navigateToLoginActivity()
                }

                is UiState.Failure -> {
                    snackBar(binding.root, state.errorMessage)
                }

                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun navigateToLoginActivity() {
        Intent().also {
            setResult(RESULT_OK, it)
            finish()
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        hideKeyboardOnTouch(ev)
        return super.dispatchTouchEvent(ev)
    }
}
