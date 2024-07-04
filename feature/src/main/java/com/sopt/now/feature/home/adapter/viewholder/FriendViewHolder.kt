package com.sopt.now.feature.home.adapter.viewholder

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.feature.databinding.ItemFriendBinding
import com.sopt.now.feature.model.HomeSealedItem
import com.sopt.now.feature.util.DateKeyStorage
import com.sopt.now.feature.util.DateUtils

class FriendViewHolder(
    private val binding: ItemFriendBinding,
    private val onItemClicked: (HomeSealedItem.Friend) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var item: HomeSealedItem.Friend? = null

    init {
        binding.root.setOnClickListener {
            item?.let { onItemClicked(it) }
        }
    }

    fun onBind(birthData: HomeSealedItem.Friend) {
        item = birthData
        binding.ivItemFriendProfile.setImageResource(birthData.profileImage)
        binding.tvItemFriendName.text = birthData.name

        val dateOrder = DateUtils.getDateOrder(birthData.date)
        updateBirthViewVisibility(dateOrder, birthData)
    }

    private fun updateBirthViewVisibility(dateOrder: Int, birthData: HomeSealedItem.Friend) {
        when (dateOrder) {
            DateKeyStorage.TODAY, DateKeyStorage.YESTERDAY -> {
                cvBirthViewVisibility()
                binding.tvItemFriendBirthDate.isVisible = true
                binding.tvItemFriendBirthDate.text = DateUtils.getDateString(birthData.date)
            }

            else -> setDefaultVisibility(birthData)
        }
    }

    private fun cvBirthViewVisibility() = with(binding) {
        cvMelonMusicGreen.isGone = true
        cvBirthGiftRed.isVisible = true
    }

    private fun setDefaultVisibility(birthData: HomeSealedItem.Friend) = with(binding) {
        binding.tvItemFriendBirthDate.isGone = true
        cvBirthGiftRed.isGone = true

        when {
            birthData.selfDescription.isEmpty() -> cvMelonMusicGreen.isVisible = true
            else -> {
                tvSelfDescription.text = birthData.selfDescription
                cvMelonMusicGreen.isVisible = true
            }
        }
    }
}
