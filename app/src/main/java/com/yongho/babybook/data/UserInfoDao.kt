package com.yongho.babybook.data

import android.content.Context
import java.time.LocalDate

object UserInfoDao {

    private const val SHARED_PREFERENCES_NAME = "UserInfo"
    private const val SHARED_PREFERENCES_BIRTH_DAY_KEY = "birthday"

    fun getBirthday(context: Context): LocalDate? {
        val sharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val birthDayStr = sharedPreferences.getString(SHARED_PREFERENCES_BIRTH_DAY_KEY, null)

        return if (birthDayStr != null) LocalDate.parse(birthDayStr) else null
    }

    fun setBirthday(context: Context, birthDay: LocalDate) {
        val sharedPreferencesEditor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
        sharedPreferencesEditor.putString(SHARED_PREFERENCES_BIRTH_DAY_KEY, birthDay.toString())
        sharedPreferencesEditor.apply()
    }
}