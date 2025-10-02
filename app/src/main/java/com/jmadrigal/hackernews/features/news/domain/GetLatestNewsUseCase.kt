package com.jmadrigal.hackernews.features.news.domain

import com.jmadrigal.hackernews.features.news.data.NewsRepository
import jakarta.inject.Inject

class GetLatestNewsUseCase @Inject constructor(
    private val repository: NewsRepository){

    suspend operator fun invoke() =
        repository.getLatestNews()
}