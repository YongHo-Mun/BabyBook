package com.yongho.babybook.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yongho.babybook.data.Page
import com.yongho.babybook.repository.PageRepository
import kotlinx.coroutines.launch

class PageViewModel @ViewModelInject constructor(private val repository: PageRepository) : ViewModel() {

    var currentPage: Page = Page("", null, null)

    val pageList by lazy {
        repository.getAll().asLiveData()
    }

    fun setCurrentPage(date: String) {
        viewModelScope.launch {
            val currentPageData = repository.getPageByDate(date)

            if (currentPageData != null) {
                currentPage = currentPageData
            } else {
                currentPage.date = date
            }
        }
    }

    fun saveCurrentPage() {
        insert(currentPage)
    }

    fun insert(page: Page) {
        viewModelScope.launch {
            repository.insert(page)
        }
    }

    fun delete(page: Page) {
        viewModelScope.launch {
            repository.delete(page)
        }
    }
}