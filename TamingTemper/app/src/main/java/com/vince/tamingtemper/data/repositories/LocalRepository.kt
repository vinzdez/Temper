package com.vince.tamingtemper.data.repositories

import android.content.Context
import com.vince.tamingtemper.data.entities.response.LevelResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor() {

    fun fetchAllLevels(context: Context): LevelResponse? {
        val apiInterface = ApiInterface.create()
        return apiInterface.getLevels(context)
    }

}