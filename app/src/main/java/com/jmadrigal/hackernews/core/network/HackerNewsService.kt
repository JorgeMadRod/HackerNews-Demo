package com.jmadrigal.hackernews.core.network

import com.jmadrigal.hackernews.features.news.data.HitsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsService {

    @GET("search_by_date")
    suspend fun getLastStories(@Query("query") query: String): HitsResponse
}