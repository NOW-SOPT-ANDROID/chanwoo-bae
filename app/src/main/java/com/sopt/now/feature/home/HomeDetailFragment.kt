package com.sopt.now.feature.home

import android.os.Build
import androidx.core.view.isVisible
import com.sopt.now.R
import com.sopt.now.core.base.BindingFragment
import com.sopt.now.databinding.FragmentMyPageBinding
import com.sopt.now.feature.model.HomeSealedItem
import com.sopt.now.feature.util.KeyStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    override fun initView() {
        updateHomeDetailUI(initGetFriendData())
    }

    private fun initGetFriendData(): HomeSealedItem.Friend? {
        return arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(KeyStorage.USER_INPUT, HomeSealedItem.Friend::class.java)
            } else {
                it.getParcelable(KeyStorage.USER_INPUT)
            }
        }
    }

    private fun updateHomeDetailUI(friend: HomeSealedItem.Friend?) = with(binding) {
        friend?.let {
            ivMainProfile.setImageResource(friend.profileImage)
            tvMainIdData.text = friend.name
            tvMainNicknameData.text = friend.name
            tvMainMbtiData.text = friend.date.toString()
            tvMainPwdData.text = friend.selfDescription
            tvMainSignOut.isVisible = false
            tvMainClearInfo.isVisible = false
        }
    }
}
