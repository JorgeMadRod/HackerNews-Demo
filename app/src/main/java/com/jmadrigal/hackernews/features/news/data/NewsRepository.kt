package com.jmadrigal.hackernews.features.news.data

interface NewsRepository {
    suspend fun getLatestNews() : List<Hit>
    suspend fun getLocalLatestNews() : List<Hit>
    suspend fun deleteNews(id:String)
}