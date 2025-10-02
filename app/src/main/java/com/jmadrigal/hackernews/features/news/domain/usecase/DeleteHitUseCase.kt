package com.jmadrigal.hackernews.features.news.domain.usecase

import com.jmadrigal.hackernews.features.news.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteHitUseCase @Inject constructor(
    private val repository: NewsRepository) {

    suspend operator fun invoke(id:String) =
        repository.deleteNews(id)
}