package com.vince.tamingtemper.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vince.tamingtemper.R
import com.vince.tamingtemper.data.entities.Level
import com.vince.tamingtemper.databinding.FragmentHomeBinding
import com.vince.tamingtemper.ui.adapter.LevelAdapter
import com.vince.tamingtemper.ui.viewmodel.LevelViewModel
import com.vince.tamingtemper.ui.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: LevelViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var levelAdapter: LevelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        viewModel.levels.observe(viewLifecycleOwner) { state ->
            when (state.status) {
                Status.SUCCESS -> {
                    state.data?.let {
                        setupHomeScreen(it)
                    }
                }

                Status.LOADING -> {
                    //TODO: Do loading
                }

                Status.ERROR -> {
                    //TODO: Notify Something went wrong
                }
            }
        }
        return binding.root
    }

    private fun setupHomeScreen(levels: List<Level>) {
        setupWeeklyHeader()
        levelAdapter = LevelAdapter(levels)
        binding.levelRecycler.adapter = levelAdapter


    }

    private fun setupWeeklyHeader() {
        val todayIndex = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7

        with(binding) {
            val weeklyUIList = listOf(
                Pair("Mon", mon),
                Pair("Tue", tue),
                Pair("Wed", wed),
                Pair("Thur", thur),
                Pair("Fri", fri),
                Pair("Sat", sat),
                Pair("Sun", sun)
            )

            for (day in weeklyUIList) {
                day.second.day.text = day.first
            }
            val currentDay = weeklyUIList[todayIndex].second

            currentDay.activeDay.setBackgroundResource(R.drawable.circle_active)
            currentDay.day.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary
                )
            )
        }
    }

}