package com.sopt.now.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.feature.databinding.ItemTitleLineBinding
import com.sopt.now.feature.model.HomeSealedItem

class TitleLineViewHolder(private val binding: ItemTitleLineBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(titleData: HomeSealedItem.TitleLine) {
        binding.tvTitle.text = titleData.title
    }
}
