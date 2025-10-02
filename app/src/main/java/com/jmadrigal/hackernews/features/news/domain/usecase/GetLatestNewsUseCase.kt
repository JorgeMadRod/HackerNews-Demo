package com.jmadrigal.hackernews.features.news.domain.usecase

import com.jmadrigal.hackernews.features.news.domain.repository.NewsRepository
import jakarta.inject.Inject

class GetLatestNewsUseCase @Inject constructor(
    private val repository: NewsRepository){

    suspend operator fun invoke() =
        repository.getLatestNews()
}