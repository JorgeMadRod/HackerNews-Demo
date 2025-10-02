package com.jmadrigal.hackernews.di

import com.jmadrigal.hackernews.core.database.dao.HitDao
import com.jmadrigal.hackernews.core.database.dao.HitDeletedDao
import com.jmadrigal.hackernews.core.network.HackerNewsService
import com.jmadrigal.hackernews.features.news.domain.repository.NewsRepository
import com.jmadrigal.hackernews.features.news.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesNewsRepository(hackerNewsService: HackerNewsService, dao: HitDao, deletedDao: HitDeletedDao): NewsRepository =
        NewsRepositoryImpl(hackerNewsService, dao, deletedDao)
}