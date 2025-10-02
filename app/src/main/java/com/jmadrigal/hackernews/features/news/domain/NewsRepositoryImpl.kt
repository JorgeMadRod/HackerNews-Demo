package com.jmadrigal.hackernews.features.news.domain

import android.util.Log
import com.jmadrigal.hackernews.core.database.HitDao
import com.jmadrigal.hackernews.core.database.HitDeletedDao
import com.jmadrigal.hackernews.core.database.dto.HitDeletedModel
import com.jmadrigal.hackernews.core.database.dto.HitModel
import com.jmadrigal.hackernews.core.network.HackerNewsService
import com.jmadrigal.hackernews.features.news.data.Hit
import com.jmadrigal.hackernews.features.news.data.NewsRepository
import com.jmadrigal.hackernews.utils.Constants

open class NewsRepositoryImpl(
    private val service: HackerNewsService,
    private val dao: HitDao,
    private val deletedDao: HitDeletedDao) : NewsRepository {

    override suspend fun getLatestNews(): List<Hit> {
        return try {
            val response = service.getLastStories(Constants.LATEST_NEWS_QUERY).hits
            val latestNews = response.map { HitModel.Companion.fromHit(it) }
            dao.saveLatestNews(latestNews)
            val result = getLocalLatestNews()
            Log.v("--->", "Entregamos: ${result.size}")
            result

        } catch (ex: Exception) {
            Log.v("--->", "Ex")
            ex.printStackTrace()
            val result = getLocalLatestNews()
            Log.v("--->", "Entregamos: ${result.size}")
            result
        }
    }

    override suspend fun getLocalLatestNews(): List<Hit> {
        val localNews = dao.getLatestNews()
        val deletedNews = deletedDao.getDeletedHits()
            Log.v("--->", "locales: ${localNews.size}")
            Log.v("--->", "deleted: ${deletedNews.size}")
        if (localNews.isNullOrEmpty()) return emptyList() else {
            val filteredNews = localNews.filterNot { news ->
                deletedNews.any { it.objectID == news.objectID }
            }
            return filteredNews.map { it.toHit(it) }
        }
    }

    override suspend fun deleteNews(id: String) {
        try {
            deletedDao.saveDeletedHits(HitDeletedModel(id))
        } catch (ex:Exception){
            ex.printStackTrace()
        }
    }
}