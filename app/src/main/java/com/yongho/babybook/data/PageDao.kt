package com.yongho.babybook.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface PageDao {
    @Query("SELECT * FROM page ORDER BY date DESC")
    fun getAll(): Flow<Array<Page>>

    @Query("SELECT * FROM page WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getPagesAtMonth(startDate: LocalDate, endDate: LocalDate): Flow<Array<Page>>

    @Query("SELECT * FROM page WHERE date = :date")
    suspend fun getPageByDate(date: LocalDate): Page?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(page: Page)

    @Delete
    suspend fun delete(page: Page)
}