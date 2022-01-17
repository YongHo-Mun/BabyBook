package com.yongho.babybook.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yongho.babybook.entity.Page
import com.yongho.babybook.repository.PageRepository
import kotlinx.coroutines.launch

class PageViewModel @ViewModelInject constructor(private val repository: PageRepository) : ViewModel() {

    private val content by lazy {
        MutableLiveData<String>()
    }

    fun getAll(): LiveData<List<Page>> {
        return repository.getAll()
    }

    fun getContentByDate(date: String): LiveData<String> {
        viewModelScope.launch {
            content.value = repository.getContentByDate(date)
        }

        return content
    }

    fun insert(page: Page) {
        repository.insert(page)
    }

    fun delete(page: Page) {
        repository.delete(page)
    }
}