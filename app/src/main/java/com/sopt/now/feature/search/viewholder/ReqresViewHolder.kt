package com.sopt.now.feature.search.viewholder

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.domain.entity.ReqresEntity

class ReqresViewHolder(
    private val binding: ItemFriendBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(reqresData: ReqresEntity) = with(binding) {
        ivItemFriendProfile.load(reqresData.avatar)
        tvItemFriendName.text = "${reqresData.lastName} ${reqresData.firstName}"
        tvItemFriendBirthDate.text = reqresData.email
        tvSelfDescription.text = "${reqresData.id}"
        cvBirthViewVisibility()
    }

    private fun cvBirthViewVisibility() = with(binding) {
        cvMelonMusicGreen.isVisible = true
        cvBirthGiftRed.isGone = true
    }
}
