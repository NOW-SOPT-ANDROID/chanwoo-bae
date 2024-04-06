package com.sopt.now.feature

import android.content.Context
import android.content.Intent
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.intent.getSafeParcelable
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.feature.model.User
import com.sopt.now.feature.util.KeyStorage
import com.sopt.now.feature.util.KeyStorage.TOTAL_PRESSED_TIME
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var backPressedTime = 0L
    private val backPressedFlow = MutableSharedFlow<Unit>()

    override fun initView() {
        initReceiveUserData()
        initBackDoublePressed()
    }

    private fun initReceiveUserData() {
        intent?.getSafeParcelable<User>(name = KeyStorage.USER_INPUT)?.let { receivedUserInput ->
            updateUI(receivedUserInput)
        }
    }

    private fun updateUI(receivedUserInput: User) = with(binding) {
        tvMainIdData.text = receivedUserInput.id
        tvMainPwdData.text = receivedUserInput.password
        tvMainNicknameData.text = receivedUserInput.nickName
        tvMainMbtiData.text = receivedUserInput.mbti
    }

    private fun initBackDoublePressed() {
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        observeBackPressedFlow()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            lifecycleScope.launch {
                backPressedFlow.emit(Unit)
            }
        }
    }

    private fun observeBackPressedFlow() {
        lifecycleScope.launch {
            backPressedFlow.flowWithLifecycle(lifecycle).onEach {
                handleBackPressed()
            }.launchIn(lifecycleScope)
        }
    }

    private fun handleBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime <= TOTAL_PRESSED_TIME) {
            finish()
        } else {
            backPressedTime = currentTime
            snackBar(binding.root, getString(R.string.main_back_once_pressed_exit))
        }
    }

    companion object {
        fun createIntent(context: Context, user: User): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(KeyStorage.USER_INPUT, user)
            }
        }
    }
}
