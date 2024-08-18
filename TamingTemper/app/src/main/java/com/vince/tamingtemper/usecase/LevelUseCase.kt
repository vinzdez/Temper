package com.vince.tamingtemper.usecase

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vince.tamingtemper.data.entities.Level
import com.vince.tamingtemper.data.entities.response.LevelResponse
import com.vince.tamingtemper.data.repositories.LocalRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LevelUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @ApplicationContext private val context: Context // Inject ApplicationContext
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun getAllLevels(): List<Level> {
        return try {
            // Retrieve cached levels from SharedPreferences
            val cachedLevelsJson = sharedPreferences.getString("cached_levels", null)
            if (cachedLevelsJson != null) {
                // Convert the JSON string back to a list of Level objects
                fromJson(cachedLevelsJson)
            } else {
                // Fetch levels from local repository and cache them
                val levelsResponse = localRepository.fetchAllLevels(context)
                levelsResponse?.levels?.let {
                    saveLevelsToCache(it)
                    it
                } ?: listOf()
            }
        } catch (e: Exception) {
            listOf() // Handle any exceptions, returning an empty list if something goes wrong
        }
    }

    private fun saveLevelsToCache(levels: List<Level>) {
        val levelsJson = toJson(levels)
        sharedPreferences.edit().putString("cached_levels", levelsJson).apply()
    }

    private fun toJson(levels: List<Level>): String {
        val gson = Gson() // Using Gson for JSON conversion
        return gson.toJson(levels)
    }

    private fun fromJson(json: String): List<Level> {
        val gson = Gson()
        val type = object : TypeToken<List<Level>>() {}.type
        return gson.fromJson(json, type)
    }
}
