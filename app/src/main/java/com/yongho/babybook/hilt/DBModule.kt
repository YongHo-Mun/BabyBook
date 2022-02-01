package com.yongho.babybook.hilt

import android.content.Context
import androidx.room.Room
import com.yongho.babybook.data.PageDao
import com.yongho.babybook.data.PageDatabase
import com.yongho.babybook.data.PageTypeConverter
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
    fun providePageDatabase(@ApplicationContext context: Context): PageDatabase = Room.databaseBuilder(context, PageDatabase::class.java, "page.db").addTypeConverter(PageTypeConverter(context.contentResolver)).build()

    @Singleton
    @Provides
    fun providePageDao(pageDatabase: PageDatabase): PageDao = pageDatabase.pageDao()
}