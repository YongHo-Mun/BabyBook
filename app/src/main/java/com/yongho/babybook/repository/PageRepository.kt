package com.yongho.babybook.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.yongho.babybook.database.PageDatabase
import com.yongho.babybook.entity.Page
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageRepository(application: Application) {
    private val pageDatabase = PageDatabase.getInstance(application)
    private val pageDao = pageDatabase.pageDao()
    private val pages = pageDao.getAll()

    private val dbIOCoroutineScope = CoroutineScope(Dispatchers.IO)

    fun getAll(): LiveData<List<Page>> {
        return pages
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