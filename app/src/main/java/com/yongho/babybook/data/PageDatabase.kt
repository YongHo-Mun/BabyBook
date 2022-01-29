package com.yongho.babybook.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import javax.inject.Singleton

@Singleton
@Database(entities = [Page::class], version = 1)
@TypeConverters(PageTypeConverter::class)
abstract class PageDatabase: RoomDatabase() {
    abstract fun pageDao(): PageDao
}