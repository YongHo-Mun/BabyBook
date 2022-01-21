package com.yongho.babybook.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "page")
data class Page(
    @PrimaryKey
    var date: String,
    var content: String?
)
