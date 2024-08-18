package com.vince.tamingtemper.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vince.tamingtemper.data.entities.Level
import com.vince.tamingtemper.usecase.LevelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject internal constructor(
    private val useCase: LevelUseCase
) : ViewModel() {

    val levels: LiveData<State<List<Level>>> get() = _levels
    private val _levels = MutableLiveData<State<List<Level>>>()

    init {
        loadLevels()
    }

    private fun loadLevels() {
        _levels.value = State.loading()
        _levels.value = State.empty()

        viewModelScope.launch {
            val state = withContext(Dispatchers.Default) {
                try {
                    State.success(useCase.getAllLevels())
                } catch (exception: Exception) {
                    State.error(exception.message)
                }
            }
            _levels.value = state
        }
    }

}