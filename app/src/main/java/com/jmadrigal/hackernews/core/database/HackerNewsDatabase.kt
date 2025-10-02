package com.jmadrigal.hackernews.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jmadrigal.hackernews.core.database.dto.HitDeletedModel
import com.jmadrigal.hackernews.core.database.dto.HitModel

@Database(entities = [HitModel::class, HitDeletedModel::class], version = 1)
abstract class HackerNewsDatabase : RoomDatabase() {
    abstract fun hitDao(): HitDao
    abstract fun hitDeletedDao(): HitDeletedDao
}