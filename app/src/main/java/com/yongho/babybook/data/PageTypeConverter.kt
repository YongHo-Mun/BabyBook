package com.yongho.babybook.data

import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson

class PageTypeConverter {

    private val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun arrayUriToString(value: Array<Uri>): String = gson.toJson(value)

    @TypeConverter
    fun stringToArrayUri(value: String): Array<Uri> = gson.fromJson(value, Array<Uri>::class.java)
}