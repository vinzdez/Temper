package com.vince.tamingtemper.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vince.tamingtemper.data.entities.response.LevelResponse
import java.io.IOException
import java.io.InputStreamReader

interface ApiInterface {

    fun getLevels(context: Context): LevelResponse?

    companion object {
        fun create(): ApiInterface {
            return object : ApiInterface {

                override fun getLevels(context: Context): LevelResponse? {
                    return runCatching {
                        loadJsonFromAsset(context)
                            .takeIf { it.isNotBlank() }
                            ?.let { json -> Gson().fromJson(json, LevelResponse::class.java) }
                    }.getOrNull()
                }

                private fun loadJsonFromAsset(context: Context): String {
                    return try {
                        val inputStream = context.assets.open("data.json")
                        val reader = InputStreamReader(inputStream)
                        reader.readText()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        ""
                    }
                }
            }
        }
    }
}
