package com.jmadrigal.hackernews.features.news.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HitsResponse(
    @Expose
    @SerializedName("hits")
    var hits: List<Hit>
)
