package com.yongho.babybook.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yongho.babybook.dao.PageDao
import com.yongho.babybook.entity.Page
import javax.inject.Singleton

@Singleton
@Database(entities = [Page::class], version = 1)
abstract class PageDatabase: RoomDatabase() {
    abstract fun pageDao(): PageDao
}