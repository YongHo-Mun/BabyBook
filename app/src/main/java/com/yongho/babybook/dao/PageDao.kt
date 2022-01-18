package com.yongho.babybook.dao

import androidx.room.*
import com.yongho.babybook.entity.Page
import kotlinx.coroutines.flow.Flow

@Dao
interface PageDao {
    @Query("SELECT * FROM page ORDER BY date DESC")
    fun getAll(): Flow<Array<Page>>

    @Query("SELECT content FROM page WHERE date = :date")
    suspend fun getContentByDate(date: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(page: Page)

    @Delete
    fun delete(page: Page)
}