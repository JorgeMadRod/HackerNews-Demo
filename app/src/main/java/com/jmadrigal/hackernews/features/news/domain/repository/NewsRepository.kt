package com.jmadrigal.hackernews.features.news.domain.repository

import com.jmadrigal.hackernews.features.news.data.model.Hit
import com.jmadrigal.hackernews.utils.GenericDataState

interface NewsRepository {
    suspend fun getLatestNews() : GenericDataState<List<Hit>>
    suspend fun getLocalLatestNews() : List<Hit>
    suspend fun deleteNews(id:String)
}