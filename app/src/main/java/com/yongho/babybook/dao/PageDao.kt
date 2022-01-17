package com.yongho.babybook.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yongho.babybook.entity.Page

@Dao
interface PageDao {
    @Query("SELECT * FROM page ORDER BY date DESC")
    fun getAll(): LiveData<List<Page>>

    @Query("SELECT content FROM page WHERE date = :date")
    suspend fun getContentByDate(date: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(page: Page)

    @Delete
    fun delete(page: Page)
}