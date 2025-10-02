package com.jmadrigal.hackernews.core.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jmadrigal.hackernews.features.news.data.Hit

@Entity(tableName = "latestNews")
data class HitModel(
    @PrimaryKey
    val objectID: String,
    val title: String,
    val author: String,
    val createdAt: String,
    val url: String
) {
    companion object {
        fun fromHit(hit: Hit): HitModel {
            return HitModel(
                hit.objectID!!,
                hit.storyTitle ?: "[NO_TITLE]",
                hit.author ?: "",
                hit.createdAt ?: "",
                hit.storyUrl ?: "")
        }
    }

    fun toHit(model: HitModel): Hit {
        return Hit(model.author,
            model.createdAt,
            model.objectID,
            model.title,
            model.url)
    }
}