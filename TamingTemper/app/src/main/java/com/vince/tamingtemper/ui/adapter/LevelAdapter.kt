package com.vince.tamingtemper.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vince.tamingtemper.data.entities.Activities
import com.vince.tamingtemper.data.entities.Challenge
import com.vince.tamingtemper.data.entities.Level
import com.vince.tamingtemper.ui.viewholder.HeaderViewHolder
import com.vince.tamingtemper.ui.viewholder.LessonViewHolder

class LevelAdapter(private val levels: List<Level>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_LESSON = 1
    }

    private val challenges: MutableList<Challenge> = mutableListOf()

    init {
        challenges.clear()
        levels.forEachIndexed { index, level ->
            challenges.add(Challenge(index, null, false))
            level.activities.chunked(2).forEachIndexed { chunkIndex, chunk ->
                val activityIndices = if (chunk.size == 2) {
                    chunk.mapIndexed { index, _ -> chunkIndex * 2 + index }
                } else {
                    listOf(chunkIndex * 2, -1)
                }
                challenges.add(Challenge(index, activityIndices, false))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                HeaderViewHolder.create(parent)
            }

            else -> {
                LessonViewHolder.create(parent)
            }
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        levels.forEach { level ->
            val activitiesCount = level.activities.size
            count += 1 + (activitiesCount + 1) / 2
        }
        return count
    }

    private fun getFlatPosition(position: Int): Int {
        var currentIndex = 0
        levels.forEach { level ->
            if (position == currentIndex) {
                return -1 // Header position
            }
            currentIndex++
            val activityPairCount = (level.activities.size + 1) / 2
            if (position in currentIndex until currentIndex + activityPairCount) {
                val flatPosition = (position - currentIndex) * 2
                return flatPosition
            }
            currentIndex += activityPairCount
        }
        return -1
    }

    override fun getItemViewType(position: Int): Int {
        return if (getFlatPosition(position) == -1) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_LESSON
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val challenge = challenges[position]
        val level = levels[challenge.parentPosition]
        if (holder is HeaderViewHolder) {
            holder.bind(level)
        } else if (holder is LessonViewHolder) {
            challenges[position].pairPosition?.let { activityPositions ->
                var leftActivities: Activities? = null
                var rightActivities: Activities? = null

                if (activityPositions[0] != -1) {
                    leftActivities = level.activities[activityPositions[0]]
                }
                if (activityPositions[1] != -1) {
                    rightActivities = level.activities[activityPositions[1]]
                }

                holder.bind(leftActivities, rightActivities)
            }
        }
    }
}
