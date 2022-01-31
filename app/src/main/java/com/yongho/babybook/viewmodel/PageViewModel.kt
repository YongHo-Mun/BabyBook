package com.yongho.babybook.viewmodel

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yongho.babybook.data.Page
import com.yongho.babybook.repository.PageRepository
import kotlinx.coroutines.launch

class PageViewModel @ViewModelInject constructor(private val repository: PageRepository) : ViewModel() {

    var currentPage = MutableLiveData(Page("", "", listOf()))

    val pageList by lazy {
        repository.getAll().asLiveData()
    }

    fun setCurrentPage(date: String) {
        viewModelScope.launch {
            var currentContent = ""
            var currentImageList = listOf<Uri>()

            val queryData = repository.getPageByDate(date)ㅇ

            if (queryData != null) {
                queryData.content.let {
                    currentContent = it
                }

                queryData.imageList.let {
                    currentImageList = it
                }
            }

            currentPage.value = Page(date, currentContent, currentImageList)
        }
    }

    fun saveCurrentPage() {
        val currentPage = this.currentPage.value!!
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