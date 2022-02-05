package com.yongho.babybook

import android.net.Uri
import com.yongho.babybook.data.Page
import com.yongho.babybook.data.PageDao
import com.yongho.babybook.hilt.DBModule
import com.yongho.babybook.repository.PageRepository
import dagger.hilt.android.testing.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.mockito.Mockito
import javax.inject.Inject

@UninstallModules(DBModule::class)
@HiltAndroidTest
class PageRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val dummyPageDao: PageDao = Mockito.mock(PageDao::class.java)

    @Inject
    lateinit var pageRepository: PageRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun getAll_oneData_returnOneData() = runBlocking {
        stubAsOneDummyData()

        val returnedPageList = pageRepository.getAll().first()

        Assert.assertEquals(returnedPageList.size, 1)
    }

    @Test
    fun insert_dataInserted_needToCall() = runBlocking {
        val dummyPageData = getDummyPageData()
        pageRepository.insert(dummyPageData)

        Mockito.verify(dummyPageDao).insert(dummyPageData)
    }

    private fun stubAsOneDummyData() {
        Mockito.`when`(dummyPageDao.getAll()).thenReturn(flow {
            val dummyPageList = arrayOf(getDummyPageData())
            emit(dummyPageList)
        })
    }

    private fun getDummyPageData(date: String = "2022-02-04", content: String = "Today's content", imageList: List<Uri> = listOf()): Page {
        return Page(date, content, imageList)
    }
}