package com.yongho.babybook.viewmodel

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.switchMap
import com.yongho.babybook.data.Page
import com.yongho.babybook.repository.PageRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class BabyBookViewModel @ViewModelInject constructor(private val repository: PageRepository) : ViewModel() {

    val currentPage = MutableLiveData(Page(LocalDate.now(), "", listOf()))
    val selectedMonth = MutableLiveData(LocalDate.now())

    val pageList by lazy {
        switchMap(selectedMonth) {
            repository.getPagesAtMonth(it).asLiveData()
        }
    }

    fun setCurrentPage(date: LocalDate) {
        viewModelScope.launch {
            var currentContent = ""
            var currentImageList = listOf<Uri>()

            val queryData = repository.getPageByDate(date)

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