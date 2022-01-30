package com.yongho.babybook.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page")
data class Page(
    @PrimaryKey
    var date: String,
    var content: String?,
    var imageList: List<Uri>?
)
