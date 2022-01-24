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
    fun getAll_initialUse_shouldBeEmptyDataReturned() = runBlocking {
        val actualPageDataArray = pageDao.getAll().first()
        Assert.assertEquals(actualPageDataArray.size, 0)
    }

    @Test
    fun getPageByDate_noData_nullShouldBeReturned() = runBlocking {
        val date = LocalDate.now().toString()
        val returnedData = pageDao.getPageByDate(date)

        Assert.assertNull(returnedData)
    }

    @Test
    fun insertPage_normal_shouldBeInsertedProperly() = runBlocking {
        val pageData = getDummyPageData()

        pageDao.insert(pageData)

        val actualPageDataArray = pageDao.getAll().first()
        Assert.assertEquals(actualPageDataArray.size, 1)
        Assert.assertEquals(actualPageDataArray[0], pageData)
    }

    @Test
    fun deletePage_normal_shouldBeDeletedProperly() = runBlocking {
        val pageData = getDummyPageData()
        pageDao.insert(pageData)
        pageDao.delete(pageData)

        val actualPageDataArray = pageDao.getAll().first()
        Assert.assertEquals(actualPageDataArray.size, 0)
    }

    @Test
    fun getPageByDate_normal_shouldBeGotProperly() = runBlocking {
        val pageData = getDummyPageData()
        pageDao.insert(pageData)

        Assert.assertEquals(pageDao.getPageByDate(pageData.date), pageData)
    }

    @Test
    fun insertReplace_samePrimaryKeyInserted_shouldBeReplacedAsLaterData() = runBlocking {
        val primaryKey = LocalDate.now().toString()
        val oldContent = "old my content"
        val newContent = "new my content"

        val firstPageData = getDummyPageData(primaryKey, oldContent)
        val secondPageData = getDummyPageData(primaryKey, newContent)

        pageDao.insert(firstPageData)
        pageDao.insert(secondPageData)

        val selectedPageArray = pageDao.getAll().first()
        Assert.assertEquals(selectedPageArray.size, 1)

        val actualContent = selectedPageArray[0].content
        Assert.assertEquals(actualContent, newContent)
        Assert.assertNotEquals(actualContent, oldContent)
    }

    @Test
    fun insert_aNumberOfDataInserted_dataNeedToBeInsertedProperly() = runBlocking {
        val dataInsertedCount = 10000

        repeat(dataInsertedCount) {
            val pageDummyData = getDummyPageData(it.toString(), it.toString())
            pageDao.insert(pageDummyData)
        }

        val selectedPageArray = pageDao.getAll().first()
        Assert.assertEquals(selectedPageArray.size, dataInsertedCount)
    }

    private fun getDummyPageData(date: String = LocalDate.now().toString(), content: String = "Today's my content"): Page {
        return Page(date, content)
    }
}