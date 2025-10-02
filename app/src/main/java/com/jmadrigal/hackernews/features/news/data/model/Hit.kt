package com.jmadrigal.hackernews.features.news.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Hit(

    @Expose
    @SerializedName("author")
    val author: String?,

    @Expose
    @SerializedName("created_at")
    val createdAt: String?,

    @Expose
    @SerializedName("objectID")
    val objectID: String?,

    @Expose
    @SerializedName("story_title")
    val storyTitle: String?,

    @Expose
    @SerializedName("story_url")
    val storyUrl: String?,

)