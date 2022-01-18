package com.yongho.babybook.repository

import androidx.lifecycle.LiveData
import com.yongho.babybook.dao.PageDao
import com.yongho.babybook.entity.Page
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageRepository @Inject constructor(private val pageDao: PageDao) {

    private val dbIOCoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getAll(): LiveData<List<Page>> {
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