package com.sopt.now.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sopt.now.core.view.ItemDiffCallback
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemMyProfileBinding
import com.sopt.now.databinding.ItemTitleLineBinding
import com.sopt.now.feature.home.adapter.viewholder.FriendViewHolder
import com.sopt.now.feature.home.adapter.viewholder.ProfileViewHolder
import com.sopt.now.feature.home.adapter.viewholder.TitleLineViewHolder
import com.sopt.now.feature.model.HomeSealedItem
import java.lang.IllegalArgumentException

class HomeMultiAdapter(
    private val onProfileClicked: (HomeSealedItem.MyProfile) -> Unit,
    private val onFriendClicked: (HomeSealedItem.Friend) -> Unit
) : ListAdapter<HomeSealedItem, ViewHolder>(homeDiffCallback) {

    fun setFriendList(friendList: List<HomeSealedItem>) {
        submitList(friendList.toMutableList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            PROFILE_VIEW -> {
                val profileBinding = ItemMyProfileBinding.inflate(inflater, parent, false)
                ProfileViewHolder(profileBinding, onProfileClicked) // 프로필 뷰 홀더 생성
            }

            FRIEND_VIEW -> {
                val friendBinding = ItemFriendBinding.inflate(inflater, parent, false)
                FriendViewHolder(friendBinding, onFriendClicked) // 친구 뷰 홀더 생성
            }

            TITLE_LINE_VIEW -> {
                val titleLineBinding = ItemTitleLineBinding.inflate(inflater, parent, false)
                TitleLineViewHolder(titleLineBinding) // 타이틀 라인 뷰 홀더 생성
            }

            else -> throw IllegalArgumentException("invalid item type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> holder.onBind(currentList[position] as HomeSealedItem.MyProfile)
            is FriendViewHolder -> holder.onBind(currentList[position] as HomeSealedItem.Friend)
            is TitleLineViewHolder -> holder.onBind(
                currentList[position] as HomeSealedItem.TitleLine
            )

            else -> throw IllegalArgumentException("invalid item type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is HomeSealedItem.MyProfile -> PROFILE_VIEW
            is HomeSealedItem.Friend -> FRIEND_VIEW
            is HomeSealedItem.TitleLine -> TITLE_LINE_VIEW
            else -> throw IllegalArgumentException("invalid item type")
        }
    }

    companion object {
        const val PROFILE_VIEW = 0
        const val FRIEND_VIEW = 1
        const val TITLE_LINE_VIEW = 2
        private val homeDiffCallback =
            ItemDiffCallback<HomeSealedItem>(
                onItemsTheSame = { old, new -> old.javaClass == new.javaClass },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
