package com.yongho.babybook.repository

import com.yongho.babybook.data.PageDao
import com.yongho.babybook.data.Page
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageRepository @Inject constructor(private val pageDao: PageDao) {

    fun getAll(): Flow<Array<Page>> {
        return pageDao.getAll()
    }

    fun getPagesAtMonth(selectedMonth: LocalDate): Flow<Array<Page>> {
        val startDate = selectedMonth.withDayOfMonth(1)
        val endDate = selectedMonth.withDayOfMonth(selectedMonth.lengthOfMonth())

        return pageDao.getPagesAtMonth(startDate, endDate)
    }

    suspend fun getPageByDate(data: LocalDate): Page? {
        return pageDao.getPageByDate(data)
    }

    suspend fun insert(page: Page) {
        pageDao.insert(page)
    }

    suspend fun delete(page: Page) {
        pageDao.delete(page)
    }
}