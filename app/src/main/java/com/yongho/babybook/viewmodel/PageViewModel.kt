package com.yongho.babybook.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.yongho.babybook.entity.Page
import com.yongho.babybook.repository.PageRepository

class PageViewModel @ViewModelInject constructor(private val repository: PageRepository, @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {

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