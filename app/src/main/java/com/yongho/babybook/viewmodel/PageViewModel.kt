package com.yongho.babybook.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yongho.babybook.data.Page
import com.yongho.babybook.repository.PageRepository
import kotlinx.coroutines.launch

class PageViewModel @ViewModelInject constructor(private val repository: PageRepository) : ViewModel() {

    val currentPage by lazy {
        MutableLiveData<Page>()
    }

    val pageList by lazy {
        repository.getAll().asLiveData()
    }

    fun setCurrentPage(date: String) {
        viewModelScope.launch {
            val currentPageData = repository.getPageByDate(date) ?: Page(date, null, null)
            currentPage.value = currentPageData
        }
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