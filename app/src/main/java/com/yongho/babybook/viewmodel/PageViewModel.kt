package com.yongho.babybook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.yongho.babybook.entity.Page
import com.yongho.babybook.repository.PageRepository

class PageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PageRepository(application)
    private val pages = repository.getAll()

    fun getAll(): LiveData<List<Page>> {
        return this.pages
    }

    fun insert(page: Page) {
        repository.insert(page)
    }

    fun delete(page: Page) {
        repository.delete(page)
    }
}