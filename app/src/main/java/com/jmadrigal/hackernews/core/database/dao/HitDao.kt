package com.jmadrigal.hackernews.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jmadrigal.hackernews.core.database.dto.HitModel

@Dao
interface HitDao {

    @Query("SELECT * FROM latestNews")
    suspend fun getLatestNews(): List<HitModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLatestNews(hitList: List<HitModel>)
}