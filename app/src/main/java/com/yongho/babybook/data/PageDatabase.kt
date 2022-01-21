package com.yongho.babybook.data

import androidx.room.Database
import androidx.room.RoomDatabase
import javax.inject.Singleton

@Singleton
@Database(entities = [Page::class], version = 1)
abstract class PageDatabase: RoomDatabase() {
    abstract fun pageDao(): PageDao
}