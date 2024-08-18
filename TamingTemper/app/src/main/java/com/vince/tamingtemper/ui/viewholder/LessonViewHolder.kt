package com.vince.tamingtemper.ui.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vince.tamingtemper.R
import com.vince.tamingtemper.data.entities.Activities
import com.vince.tamingtemper.databinding.ItemImageBinding

class LessonViewHolder private constructor(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {
    companion object Factory {
        fun create(parentView: ViewGroup): LessonViewHolder {
            val binding = ItemImageBinding.inflate(LayoutInflater.from(parentView.context))
            return LessonViewHolder(binding)
        }
    }

    fun bind(leftActivity: Activities?, rightActivity: Activities?) {
        with(binding) {
            if (leftActivity != null && rightActivity != null) {
                with(leftImg) {
                    root.visibility = View.VISIBLE
                    activityImg.setImageResource(R.drawable.leftlesson)
                    levelTitle.text = leftActivity.title
                }
                with(rightImg) {
                    root.visibility = View.VISIBLE
                    activityImg.setImageResource(R.drawable.rightlesson)
                    levelTitle.text = rightActivity.title
                }
            }

            if (leftActivity != null && rightActivity == null) {
                leftImg.activityImg.setImageResource(R.drawable.centerlesson)
                leftImg.checkmark.visibility = View.GONE
                leftImg.levelTitle.text = leftActivity.title
                rightImg.root.visibility = View.GONE

            }
        }
    }
}