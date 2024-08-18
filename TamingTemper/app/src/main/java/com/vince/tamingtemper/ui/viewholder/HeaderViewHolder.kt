package com.vince.tamingtemper.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vince.tamingtemper.data.entities.Level
import com.vince.tamingtemper.databinding.ItemHeaderBinding

class HeaderViewHolder private constructor(
    private val binding: ItemHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object Factory {
        fun create(parentView: ViewGroup): HeaderViewHolder {
            val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parentView.context))
            return HeaderViewHolder(binding)
        }
    }

    fun bind(level: Level) {
        with(binding) {
            levelTitle.text = level.title
            levelDescription.text = level.description
        }
    }
}