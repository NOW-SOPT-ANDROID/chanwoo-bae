package com.sopt.now.feature.mypage

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sopt.now.R
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.core.util.fragment.snackBar
import com.sopt.now.core.util.fragment.toast
import com.sopt.now.core.util.intent.navigateTo
import com.sopt.now.core.view.UiState
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.domain.entity.UserEntity
import com.sopt.now.feature.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel by viewModels<MyPageViewModel>()
    override fun initView() {
        initBtnClickListener()
        initObserveMemberProfileState()
    }

    private fun initBtnClickListener() {
        initSignOutBtnClickListener()
        initClearInfoBtnClickListener()
    }

    private fun initObserveMemberProfileState() {
        viewModel.getMemeberProfile()
        viewModel.memberProfileState.flowWithLifecycle(lifecycle).onEach { state ->
            when (state) {
                is UiState.Success -> initUpdateUserDataUI(state.data)
                is UiState.Failure -> snackBar(binding.root, state.errorMessage)
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

    private fun initSignOutBtnClickListener() {
        binding.tvMainSignOut.setOnClickListener {
            viewModel.updateCheckLoginState(-1)
            toast(getString(R.string.login_completed, getString(R.string.main_logout_under_bar)))
            navigateTo<LoginActivity>(requireContext())
        }
    }

    private fun initClearInfoBtnClickListener() {
        binding.tvMainClearInfo.setOnClickListener {
            viewModel.clearSharedPrefUserInfo()
            toast(
                getString(
                    R.string.login_completed,
                    getString(R.string.main_clear_user_under_bar)
                )
            )
            navigateTo<LoginActivity>(requireContext())
        }
    }

    private fun initUpdateUserDataUI(data: UserEntity) = with(binding) {
        tvMainIdData.text = data.id
        tvMainPwdData.text = data.password
        tvMainNicknameData.text = data.nickName
        tvMainMbtiData.text = data.phone
    }
}
