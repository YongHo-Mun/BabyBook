package com.yongho.babybook.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page")
data class Page(
    @PrimaryKey
    val date: String,
    val content: String?,
    val imageList: Array<Uri>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Page

        if (date != other.date) return false
        if (content != other.content) return false
        if (imageList != null) {
            if (other.imageList == null) return false
            if (!imageList.contentEquals(other.imageList)) return false
        } else if (other.imageList != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (imageList?.contentHashCode() ?: 0)
        return result
    }
}
