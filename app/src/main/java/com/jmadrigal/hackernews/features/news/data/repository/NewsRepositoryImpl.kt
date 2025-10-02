package com.jmadrigal.hackernews.features.news.data.repository

import com.jmadrigal.hackernews.core.database.dao.HitDao
import com.jmadrigal.hackernews.core.database.dao.HitDeletedDao
import com.jmadrigal.hackernews.core.database.dto.HitDeletedModel
import com.jmadrigal.hackernews.core.database.dto.HitModel
import com.jmadrigal.hackernews.core.network.HackerNewsService
import com.jmadrigal.hackernews.features.news.data.model.Hit
import com.jmadrigal.hackernews.features.news.domain.repository.NewsRepository
import com.jmadrigal.hackernews.utils.Constants
import com.jmadrigal.hackernews.utils.GenericDataState

open class NewsRepositoryImpl(
    private val service: HackerNewsService,
    private val dao: HitDao,
    private val deletedDao: HitDeletedDao) : NewsRepository {

    override suspend fun getLatestNews(): GenericDataState<List<Hit>> {
        return try {
            val response = service.getLastStories(Constants.LATEST_NEWS_QUERY).hits
            val latestNews = response.map { HitModel.Companion.fromHit(it) }
            dao.saveLatestNews(latestNews)
            val result = getLocalLatestNews()
            GenericDataState.Success(result)

        } catch (ex: Exception) {
            ex.printStackTrace()
            val result = getLocalLatestNews()
            GenericDataState.Success(result)
        }
    }

    override suspend fun getLocalLatestNews(): List<Hit> {
        val localNews = dao.getLatestNews()
        val deletedNews = deletedDao.getDeletedHits()
        if (localNews.isEmpty()) return emptyList() else {
            val filteredNews = localNews.filterNot { news ->
                deletedNews.any { it.objectID == news.objectID }
            }
            return filteredNews.map { it.toHit(it) }
        }
    }

    override suspend fun deleteNews(id: String) {
        try {
            deletedDao.saveDeletedHits(HitDeletedModel(id))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}