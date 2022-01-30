package com.yongho.babybook.viewmodel

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yongho.babybook.data.Page
import com.yongho.babybook.repository.PageRepository
import kotlinx.coroutines.launch

class PageViewModel @ViewModelInject constructor(private val repository: PageRepository) : ViewModel() {

    var currentDate = MutableLiveData("")
    var currentContent = MutableLiveData("")
    var currentImageList = MutableLiveData(listOf<Uri>())

    val pageList by lazy {
        repository.getAll().asLiveData()
    }

    fun setCurrentPage(date: String) {
        currentDate.value = date
        viewModelScope.launch {
            val queryData = repository.getPageByDate(date)

            if (queryData != null) {
                queryData.content.let {
                    currentContent.value = it
                }

                queryData.imageList.let {
                    currentImageList.value = it
                }
            }
        }
    }

    fun saveCurrentPage() {
        val currentPage = Page(currentDate.value!!, currentContent.value!!, currentImageList.value!!)
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