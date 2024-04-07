package com.sopt.now.feature

import android.content.Context
import android.content.Intent
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.intent.getSafeParcelable
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.feature.model.User
import com.sopt.now.feature.util.KeyStorage

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        initReceiveUserData()
    }

    private fun initReceiveUserData() {
        val receivedUserInput = intent?.getSafeParcelable<User>(name = KeyStorage.USER_INPUT)
        if (receivedUserInput != null) {
            updateUI(receivedUserInput)
        }
    }

    private fun updateUI(receivedUserInput: User) = with(binding) {
        tvMainIdData.text = receivedUserInput.id
        tvMainPwdData.text = receivedUserInput.password
        tvMainNicknameData.text = receivedUserInput.nickName
        tvMainMbtiData.text = receivedUserInput.mbti
    }

    companion object {
        fun createIntent(context: Context, user: User): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(KeyStorage.USER_INPUT, user)
            }
        }
    }
}
