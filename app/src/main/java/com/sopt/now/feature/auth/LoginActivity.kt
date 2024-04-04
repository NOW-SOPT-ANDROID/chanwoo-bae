package com.sopt.now.feature.auth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.core.util.intent.getSafeParcelable
import com.sopt.now.databinding.ActivityLoginBinding
import com.sopt.now.feature.MainActivity
import com.sopt.now.feature.model.User
import com.sopt.now.feature.util.KeyStorage

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun initView() {
        initRegisterResultLauncher()
        initBtnClickListener()
    }

    private fun initRegisterResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val receivedUserInput =
                        result.data?.getSafeParcelable<User>(name = KeyStorage.USER_INPUT)
                    if (receivedUserInput != null) {
                        initLoginBtnClickListener(receivedUserInput)
                    }
                }
            }
    }

    private fun initBtnClickListener() {
        initLoginBtnClickListener(userData = null)
        initSignUpBtnClickListener()
    }

    private fun initLoginBtnClickListener(userData: User?) {
        binding.btnLogin.setOnClickListener {
            handleLoginState(userData)
        }
    }

    private fun handleLoginState(userData: User?) = with(binding) {
        if (userData == null) {
            toast(getString(R.string.login_first_sign_up_null))
            return
        }
        when {
            userData.id != etLoginId.text.toString() -> {
                snackBar(root, getString(R.string.login_not_id_equal))
            }

            userData.password != etLoginPwd.text.toString() -> {
                snackBar(root, getString(R.string.login_not_pwed_equal))
            }

            else -> {
                navigateToMainActivity(userData)
                toast(getString(R.string.login_completed))
            }
        }
    }

    private fun navigateToMainActivity(userData: User) {
        val intent = MainActivity.createIntent(this@LoginActivity, userData)
        startActivity(intent)
        finish()
    }

    private fun initSignUpBtnClickListener() {
        binding.tvLoginSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}
