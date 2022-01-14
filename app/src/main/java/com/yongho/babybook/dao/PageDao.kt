package com.yongho.babybook.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yongho.babybook.entity.Page

@Dao
interface PageDao {
    @Query("SELECT * FROM page ORDER BY date DESC")
    fun getAll(): LiveData<List<Page>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(page: Page)

    @Delete
    fun delete(page: Page)
}