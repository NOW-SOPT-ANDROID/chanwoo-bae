package com.sopt.now.feature.auth

import android.content.Intent
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.context.toast
import com.sopt.now.databinding.ActivitySignUpBinding
import com.sopt.now.feature.model.User
import com.sopt.now.feature.util.KeyStorage

class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {

    override fun initView() {
        initSignUpBtnClickListener()
    }

    private fun initSignUpBtnClickListener() {
        binding.btnSignUp.setOnClickListener {
            val userInputData = getUserInputData()
            val validationMessages = validateUserInputData(userInputData)
            handleValidationResult(validationMessages, userInputData)
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
            showSnackBar(validationMessages)
        }
    }

    private fun navigateToLoginActivity(userInputData: User) {
        Intent().apply {
            putExtra(KeyStorage.USER_INPUT, userInputData)
            setResult(RESULT_OK, this)
            finish()
        }
    }

    private fun showSnackBar(messages: List<String>) {
        if (messages.isNotEmpty()) {
            snackBar(binding.root, messages.joinToString("\n"))
        }
    }

    companion object {
        const val MIN_ID_LENGTH = 6
        const val MAX_ID_LENGTH = 10
        const val MIN_PWD_LENGTH = 8
        const val MAX_PWD_LENGTH = 12
        const val MIN_NICKNAME_LENGTH = 1
        const val MBTI_LENGTH = 4
    }
}
