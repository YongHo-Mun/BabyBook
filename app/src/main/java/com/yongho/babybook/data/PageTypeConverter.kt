package com.yongho.babybook.data

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson


@ProvidedTypeConverter
class PageTypeConverter(private val contentResolver: ContentResolver) {

    private val gson by lazy {
        Gson()
    }

    @TypeConverter
    fun arrayUriToString(value: List<Uri>): String {
        Log.d(TAG, "arrayUriToString value : $value")
        val stringUriList = value.map {
            convertUriToFilePath(it)
        }

        return gson.toJson(stringUriList)
    }

    @TypeConverter
    fun stringToArrayUri(value: String): List<Uri> {
        Log.d(TAG, "stringToArrayUri value : $value")
        val stringUriList = gson.fromJson(value, Array<String>::class.java)
        return stringUriList.map {
            convertFilePathToUri(it)
        }
    }

    private fun convertUriToFilePath(uri: Uri): String {
        var cursor: Cursor? = null
        val projection = arrayOf(DATA_COLUMN_NAME)

        var resultPath = ""

        try {
            cursor = contentResolver.query(uri, projection, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(DATA_COLUMN_NAME)
                resultPath = cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }

        return resultPath
    }

    private fun convertFilePathToUri(filePath: String): Uri {
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "$DATA_COLUMN_NAME = '$filePath'", null, null)
        var resultUri = Uri.EMPTY

        if (cursor != null && cursor.moveToFirst()) {
            val columnIdx = cursor.getColumnIndex(ID_COLUMN_NAME)

            if (columnIdx >= 0) {
                val id = cursor.getInt(columnIdx)
                resultUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id.toLong())
            }

            cursor.close()
        }

        Log.d(TAG, "result URI : $resultUri")
        return resultUri
    }

    companion object {
        const val TAG = "PageTypeConverter"
        const val DATA_COLUMN_NAME = "_data"
        const val ID_COLUMN_NAME = "_id"
    }
}