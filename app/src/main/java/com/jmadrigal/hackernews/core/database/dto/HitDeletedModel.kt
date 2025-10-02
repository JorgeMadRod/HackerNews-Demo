package com.jmadrigal.hackernews.core.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jmadrigal.hackernews.features.news.data.model.HitDeleted

@Entity(tableName = "deleted")
data class HitDeletedModel(
    @PrimaryKey
    val objectID: String
) {

    companion object {
        fun fromHitDeleted(hitDeleted: HitDeleted): HitDeletedModel {
            return HitDeletedModel(hitDeleted.objectId)
        }

        fun toHitDeleted(hitDeletedModel: HitDeletedModel): HitDeleted {
            return HitDeleted(hitDeletedModel.objectID)
        }
    }
}
