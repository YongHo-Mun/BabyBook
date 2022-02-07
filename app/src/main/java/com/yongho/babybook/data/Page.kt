package com.yongho.babybook.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "page")
data class Page(
    @PrimaryKey
    var date: LocalDate,
    var content: String,
    var imageList: List<Uri>
)
