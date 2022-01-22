package com.yongho.babybook

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yongho.babybook.data.Page
import com.yongho.babybook.data.PageDao
import com.yongho.babybook.data.PageDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var pageDatabase: PageDatabase
    private lateinit var pageDao: PageDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        pageDatabase = Room.inMemoryDatabaseBuilder(context, PageDatabase::class.java).build()

        pageDao = pageDatabase.pageDao()
    }

    @After
    fun teardown() {
        pageDatabase.close()
    }

    @Test
    fun insertPage_normal_shouldBeInsertedProperly() = runBlocking {
        val pageData = Page(LocalDate.now().toString(), "Today's my content")

        pageDao.insert(pageData)

        Assert.assertEquals(pageDao.getAll().first()[0], pageData)
    }

    @Test
    fun deletePage_normal_shouldBeDeletedProperly() = runBlocking {
        val pageData = Page(LocalDate.now().toString(), "Today's my content")
        pageDao.insert(pageData)
        pageDao.delete(pageData)

        Assert.assertEquals(pageDao.getAll().first().size, 0)
    }
}