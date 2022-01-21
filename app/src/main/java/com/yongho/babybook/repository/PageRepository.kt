package com.yongho.babybook.repository

import com.yongho.babybook.data.PageDao
import com.yongho.babybook.data.Page
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageRepository @Inject constructor(private val pageDao: PageDao) {

    private val dbIOCoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getAll(): Flow<Array<Page>> {
        return pageDao.getAll()
    }

    suspend fun getContentByDate(data: String): String {
        return pageDao.getContentByDate(data)
    }

    fun insert(page: Page) {
        dbIOCoroutineScope.launch {
            pageDao.insert(page)
        }
    }

    fun delete(page: Page) {
        dbIOCoroutineScope.launch {
            pageDao.delete(page)
        }
    }
}