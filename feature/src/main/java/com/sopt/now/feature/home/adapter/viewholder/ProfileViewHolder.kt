package com.sopt.now.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.feature.databinding.ItemMyProfileBinding
import com.sopt.now.feature.model.HomeSealedItem

class ProfileViewHolder(
    private val binding: ItemMyProfileBinding,
    private val onItemClicked: (HomeSealedItem.MyProfile) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var item: HomeSealedItem.MyProfile? = null

    init {
        binding.root.setOnClickListener {
            item?.let { onItemClicked(it) }
        }
    }

    fun onBind(profileData: HomeSealedItem.MyProfile) = with(binding) {
        item = profileData
        ivItemProfile.setImageResource(profileData.myProfileImg)
        tvItemProfileName.text = profileData.myName
        tvSelfDescription.text = profileData.myDescription
    }
}
