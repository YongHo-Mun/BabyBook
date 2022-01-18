package com.yongho.babybook.module

import android.content.Context
import androidx.room.Room
import com.yongho.babybook.dao.PageDao
import com.yongho.babybook.database.PageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DBModule {

    @Singleton
    @Provides
    fun providePageDatabase(@ApplicationContext context: Context): PageDatabase = Room.databaseBuilder(context, PageDatabase::class.java, "page.db").build()

    @Singleton
    @Provides
    fun providePageDao(pageDatabase: PageDatabase): PageDao = pageDatabase.pageDao()
}