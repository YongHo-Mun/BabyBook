package com.yongho.babybook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yongho.babybook.dao.PageDao
import com.yongho.babybook.entity.Page

@Database(entities = [Page::class], version = 1)
abstract class PageDatabase: RoomDatabase() {
    abstract fun pageDao(): PageDao

    companion object {
        private var INSTANCE: PageDatabase? = null

        fun getInstance(context: Context): PageDatabase {

            if (INSTANCE == null) {
                synchronized(PageDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, PageDatabase::class.java, "page").build()
                }
            }

            return INSTANCE!!
        }
    }
}