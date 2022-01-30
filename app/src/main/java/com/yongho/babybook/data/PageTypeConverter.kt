package com.yongho.babybook.data

import android.net.Uri
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson

@ProvidedTypeConverter
class PageTypeConverter {

    private val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun arrayUriToString(value: List<Uri>?): String? {
        if (value == null) {
            return null
        }

        return gson.toJson(value.map {
            it.toString()
        })
    }

    @TypeConverter
    fun stringToArrayUri(value: String?): List<Uri>? {
        if (value == null) {
            return null
        }

        val stringUriList = gson.fromJson(value, Array<String>::class.java)
        return stringUriList.map {
            Uri.parse(it)
        }
    }
}