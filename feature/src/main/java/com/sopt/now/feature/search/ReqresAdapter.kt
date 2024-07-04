package com.sopt.now.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sopt.now.core_ui.view.ItemDiffCallback
import com.sopt.now.domain.entity.ReqresEntity
import com.sopt.now.feature.databinding.ItemFriendBinding
import com.sopt.now.feature.search.viewholder.ReqresViewHolder

class ReqresAdapter : ListAdapter<ReqresEntity, ReqresViewHolder>(reqresDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReqresViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendBinding.inflate(inflater, parent, false)
        return ReqresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReqresViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        private val reqresDiffCallback =
            ItemDiffCallback<ReqresEntity>(
                onItemsTheSame = { old, new -> old.id == new.id },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
