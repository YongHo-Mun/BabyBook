package com.yongho.babybook.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yongho.babybook.data.Page
import com.yongho.babybook.repository.PageRepository
import kotlinx.coroutines.launch

class PageViewModel @ViewModelInject constructor(private val repository: PageRepository) : ViewModel() {

    var currentPage: LiveData<Page> = MutableLiveData()

    val pageList by lazy {
        repository.getAll().asLiveData()
    }

    fun setCurrentPage(date: String) {
        val currentPageData = repository.getPageByDate(date)
        currentPage = currentPageData.asLiveData()
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