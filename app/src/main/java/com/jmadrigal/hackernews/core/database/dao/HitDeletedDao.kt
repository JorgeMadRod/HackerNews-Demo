package com.jmadrigal.hackernews.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jmadrigal.hackernews.core.database.dto.HitDeletedModel

@Dao
interface HitDeletedDao {

    @Query("SELECT * FROM deleted")
    suspend fun getDeletedHits(): List<HitDeletedModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDeletedHits(model: HitDeletedModel)
}